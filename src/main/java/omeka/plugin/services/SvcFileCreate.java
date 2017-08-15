package omeka.plugin.services;

import java.util.List;

import arc.mf.plugin.ServiceExecutor;
import arc.mf.plugin.dtype.AssetType;
import arc.mf.plugin.dtype.BooleanType;
import arc.mf.plugin.dtype.LongType;
import arc.mf.plugin.dtype.StringType;
import arc.mf.plugin.dtype.XmlDocType;
import arc.xml.XmlDoc;
import arc.xml.XmlDocMaker;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.model.Element;
import io.github.xtman.omeka.model.File;
import io.github.xtman.omeka.model.builder.FileBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcFileCreate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.file.create";

    public SvcFileCreate() {
        addToDefinition(defn, false);
        defn.add(new Interface.Element("item", LongType.POSITIVE_ONE, "item.", 1, 1));

        defn.add(new Interface.Element("id", AssetType.DEFAULT,
                "Source asset id. The asset must have content so that it can be uploaded to OMEKA as OMEKA file. If not specified, service input must specified.",
                0, 1));
        defn.add(new Interface.Element("filename", StringType.DEFAULT,
                "File name. If specified, override the value from asset metadata or service input.", 0, 1));
        defn.add(new Interface.Element("ctype", StringType.DEFAULT,
                "Content MIME type. If specified, override the value from asset metadata or service input.", 0, 1));
        defn.add(new Interface.Element("csize", LongType.DEFAULT,
                "Content length. If specified, override the value from asset metadata or service input.", 0, 1));
    }

    static void addToDefinition(Interface defn, boolean importFromAsset) {
        defn.add(new Interface.Element("order", LongType.POSITIVE_ONE, "order.", 0, 1));

        Interface.Element et = new Interface.Element("element_text", XmlDocType.DEFAULT, "Element text.", 0,
                Integer.MAX_VALUE);
        et.add(new Interface.Element("html", BooleanType.DEFAULT, "Is html? Defaults to false.", 0, 1));
        et.add(new Interface.Element("text", StringType.DEFAULT, "text.", 1, 1));
        if (importFromAsset) {
            et.add(new Interface.Element("xpath", StringType.DEFAULT, "XPath to the specified asset's metadata.", 0,
                    1));
        }

        Interface.Element e = new Interface.Element("element", XmlDocType.DEFAULT, "Element.", 1, 1);
        e.add(new Interface.Element("id", LongType.POSITIVE_ONE, "Element id", 0, 1));
        Interface.Element en = new Interface.Element("name", StringType.DEFAULT, "Element name", 0, 1);
        en.add(new Interface.Attribute("element_set", LongType.POSITIVE_ONE, "Element set id", 0));
        e.add(en);
        et.add(e);

        defn.add(et);
    }

    static FileBuilder parse(OmekaClient omekaClient, XmlDoc.Element args) throws Throwable {
        FileBuilder fb = new FileBuilder();
        if (args.elementExists("item")) {
            fb.setItemId(args.longValue("item"));
        }
        if (args.elementExists("order")) {
            fb.setOrder(args.longValue("order"));
        }

        List<XmlDoc.Element> ets = args.elements("element_text");
        if (ets != null) {
            ResultSet<Element> elements = null;
            for (XmlDoc.Element et : ets) {
                boolean html = et.booleanValue("html", false);
                String text = et.value("text");
                long elementId;
                if (et.elementExists("element/id")) {
                    elementId = et.longValue("element/id");
                } else {
                    String elementName = et.value("element/name");
                    if (elementName == null) {
                        throw new IllegalArgumentException(
                                "Missing element_text/element/id or element_text/element/name.");
                    }
                    Long elementSetId = et.longValue("element/name/@element_set", null);
                    if (elements == null) {
                        elements = omekaClient.listElements(null);
                        if (elements == null || elements.isEmpty()) {
                            throw new Exception("No element is returned from OMEKA server.");
                        }
                    }
                    elementId = SvcItemCreate.findElementByName(elements, elementName, elementSetId);
                }
                fb.addElementText(html, text, elementId);
            }
        }

        return fb;
    }

    @Override
    protected void execute(OmekaClient omekaClient, XmlDoc.Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        String assetId = args.value("id");
        FileBuilder fb = parse(omekaClient, args);
        if (inputs != null && inputs.size() > 0) {
            if (assetId != null) {
                throw new IllegalArgumentException(
                        "Both asset id and service input have been specified. Expects only one.");
            }
            Input input = inputs.input(0);
            if (input.source() != null) {
                fb.setFileName(parseFileNameFromPath(input.source()));
            }
            if (input.mimeType() != null) {
                fb.setMimeType(input.mimeType());
            }
            if (input.length() >= 0) {
                fb.setLength(input.length());
            }
            fb.setStream(input.stream());
        } else if (assetId != null) {
            buildFileFromAsset(executor(), fb, assetId);
        } else {
            throw new IllegalArgumentException("Either asset id or service input must be specified. Found none.");
        }
        if (args.elementExists("filename")) {
            fb.setFileName(args.value("filename"));
        }
        if (args.elementExists("ctype")) {
            fb.setMimeType(args.value("ctype"));
        }
        if (args.elementExists("csize")) {
            fb.setLength(args.longValue("csize"));
        }
        File f = omekaClient.createFile(fb);
        OmekaXmlUtils.saveFileXml(f, w, true);
    }

    static void buildFileFromAsset(ServiceExecutor executor, FileBuilder fb, String assetId) throws Throwable {
        Outputs os = new Outputs(1);
        XmlDocMaker dm = new XmlDocMaker("args");
        dm.add("id", assetId);
        XmlDoc.Element ae = executor.execute("asset.get", dm.root(), null, os).element("asset");
        if (os.size() != 1) {
            throw new Exception("Failed to retrieve content from asset " + assetId);
        }
        Output o = os.output(0);
        fb.setFileName(ae.value("name"));
        if (fb.fileName() == null) {
            String fn = "__asset__" + ae.value("@id");
            String ext = ae.value("content/type/@ext");
            if (ext != null) {
                fn += "." + ext;
            }
            fb.setFileName(fn);
        }
        fb.setMimeType(o.mimeType());
        if (fb.mimeType() == null) {
            fb.setMimeType(ae.value("content/type"));
        }
        fb.setLength(o.length());
        if (fb.length() < 0) {
            fb.setLength(ae.longValue("content/size"));
        }
        fb.setStream(o.stream());
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Creates a OMEKA file.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

    @Override
    public int minNumberOfInputs() {
        return 0;
    }

    @Override
    public int maxNumberOfInputs() {
        return 1;
    }

    static String parseFileNameFromPath(String source) {
        if (source != null) {
            String s = source.replace('\\', '/');
            int idx = s.lastIndexOf('/');
            if (idx >= 0) {
                return s.substring(idx + 1);
            } else {
                return s;
            }
        }
        return null;
    }

}
