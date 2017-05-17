package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.mf.plugin.dtype.StringType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Element;
import io.github.xtman.omeka.model.builder.ElementBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcElementCreate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.element.create";

    public SvcElementCreate() {
        addToDefinition(defn);
    }

    static void addToDefinition(Interface defn) {
        defn.add(new Interface.Element("order", LongType.POSITIVE_ONE, "order", 1, 1));
        defn.add(new Interface.Element("name", StringType.DEFAULT, "name", 1, 1));
        defn.add(new Interface.Element("description", StringType.DEFAULT, "description", 0, 1));
        defn.add(new Interface.Element("comment", StringType.DEFAULT, "comment", 0, 1));
        defn.add(new Interface.Element("element_set", LongType.POSITIVE_ONE, "element set id", 0, 1));
    }

    static ElementBuilder parse(XmlDoc.Element args) throws Throwable {
        ElementBuilder eb = new ElementBuilder();
        if(args.elementExists("order")){
            eb.setOrder(args.longValue("order"));
        }
        if(args.elementExists("name")){
            eb.setName(args.value("name"));
        }
        if(args.elementExists("description")){
            eb.setDescription(args.value("description"));
        }
        if(args.elementExists("comment")){
            eb.setComment(args.value("comment"));
        }
        if(args.elementExists("element_set")){
            eb.setElementSetId(args.longValue("element_set"));
        }
        return eb;
    }

    @Override
    protected void execute(OmekaClient omekaClient,XmlDoc. Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        ElementBuilder eb = parse(args);
        Element e = omekaClient.createElement(eb);
        OmekaXmlUtils.saveElementXml(e, w);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Creates an OMEKA element.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
