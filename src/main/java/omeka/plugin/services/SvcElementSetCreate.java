package omeka.plugin.services;

import arc.mf.plugin.dtype.StringType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.ElementSet;
import io.github.xtman.omeka.model.builder.ElementSetBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcElementSetCreate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.element_set.create";

    public SvcElementSetCreate() {
        addToDefinition(defn);
    }

    static void addToDefinition(Interface defn) {
        defn.add(new Interface.Element("name", StringType.DEFAULT, "name of the element set.", 1, 1));
        defn.add(new Interface.Element("description", StringType.DEFAULT, "description about the element set.", 0, 1));
    }

    static ElementSetBuilder parse(XmlDoc.Element args) throws Throwable {
        ElementSetBuilder cb = new ElementSetBuilder();
        if (args.elementExists("name")) {
            cb.setName(args.value("name"));
        }
        if (args.elementExists("description")) {
            cb.setDescription("description");
        }
        return cb;
    }

    @Override
    protected void execute(OmekaClient omekaClient, XmlDoc.Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        ElementSetBuilder esb = parse(args);
        ElementSet es = omekaClient.createElementSet(esb);
        OmekaXmlUtils.saveElementSetXml(es, w, true);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Creates an OMEKA element set.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
