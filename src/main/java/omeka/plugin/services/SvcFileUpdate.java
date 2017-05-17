package omeka.plugin.services;

import java.util.List;

import arc.mf.plugin.dtype.BooleanType;
import arc.mf.plugin.dtype.LongType;
import arc.mf.plugin.dtype.StringType;
import arc.mf.plugin.dtype.XmlDocType;
import arc.xml.XmlDoc;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.File;
import io.github.xtman.omeka.model.builder.FileBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcFileUpdate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.file.update";

    public SvcFileUpdate() {
        defn.add(new Interface.Element("id", LongType.POSITIVE_ONE, "The id of the OMEKA file.", 1, 1));
        addToDefinition(defn);
    }
    
    static void addToDefinition(Interface defn){
        defn.add(new Interface.Element("order", LongType.POSITIVE_ONE, "order.", 1,1));
        Interface.Element et = new Interface.Element("element_text", XmlDocType.DEFAULT, "Element text.", 1,
                Integer.MAX_VALUE);
        et.add(new Interface.Element("html", BooleanType.DEFAULT, "Is html? ", 0, 1));
        et.add(new Interface.Element("text", StringType.DEFAULT, "text.", 1, 1));
        et.add(new Interface.Element("element", LongType.POSITIVE_ONE, "element id", 1, 1));
        defn.add(et);
    }
    
    static FileBuilder parse(XmlDoc.Element args) throws Throwable {
        FileBuilder fb = new FileBuilder();
        if(args.elementExists("order")){
            fb.setOrder(args.longValue("order"));
        }
        List<XmlDoc.Element> ets = args.elements("element_text");
        if (ets != null) {
            for (XmlDoc.Element et : ets) {
                boolean html = et.booleanValue("html", false);
                String text = et.value("text");
                long elementId = et.longValue("element/id");
                fb.addElementText(html, text, elementId);
            }
        }
        return fb;
    }


    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        FileBuilder fb = parse(args);
        File f = omekaClient.updateFile(id, fb);
        OmekaXmlUtils.saveFileXml(f, w);
    }


    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Update the specified OMEKA file.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
