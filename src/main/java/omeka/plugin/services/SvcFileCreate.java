package omeka.plugin.services;

import java.io.InputStream;

import arc.mf.plugin.dtype.AssetType;
import arc.mf.plugin.dtype.LongType;
import arc.mf.plugin.dtype.StringType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.File;
import io.github.xtman.omeka.model.builder.FileBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcFileCreate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.file.create";

    public SvcFileCreate() {
        SvcFileUpdate.addToDefinition(defn);
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

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        String assetId = args.value("id");
        FileBuilder fb = SvcFileUpdate.parse(args);
        String filename = null;
        String ctype = null;
        Long csize = null;
        InputStream cin = null;
        if (inputs != null && inputs.size() > 0) {
            if (assetId != null) {
                throw new IllegalArgumentException(
                        "Both asset id and service input have been specified. Expects only one.");
            }
            Input input = inputs.input(0);
            if (input.source() != null) {
                filename = parseFileNameFromPath(input.source());
            }
            if (input.mimeType() != null) {
                ctype = input.mimeType();
            }
            if (input.length() >= 0) {
                csize = input.length();
            }
            cin = input.stream();
        } else if (assetId != null) {
            Outputs os = new Outputs(1);
            XmlDoc.Element ae = executor().execute("asset.get", "<args><id>" + assetId + "</id></args>", null, os)
                    .element("asset");
            if (os.size() != 1) {
                throw new Exception("Failed to retrieve content from asset " + assetId);
            }
            Output o = os.output(0);
            filename = ae.value("name");
            if (filename == null) {
                filename = assetId;
                String ext = ae.value("content/type/@ext");
                if (ext != null) {
                    filename += "." + ext;
                }
            }
            ctype = o.mimeType();
            if (ctype == null) {
                ctype = ae.value("content/type");
            }
            csize = o.length();
            if (csize < 0) {
                csize = ae.longValue("content/size");
            }
            cin = o.stream();
        } else {
            throw new IllegalArgumentException("Either asset id or service input must be specified. Found none.");
        }
        if (args.elementExists("filename")) {
            filename = args.value("filename");
        }
        if (args.elementExists("ctype")) {
            ctype = args.value("ctype");
        }
        if (args.elementExists("csize")) {
            csize = args.longValue("csize");
        }
        File f = omekaClient.createFile(fb, filename, cin, csize, ctype);
        OmekaXmlUtils.saveFileXml(f, w, true);
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
