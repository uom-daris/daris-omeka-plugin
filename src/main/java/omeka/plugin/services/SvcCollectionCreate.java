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
import io.github.xtman.omeka.model.Collection;
import io.github.xtman.omeka.model.builder.CollectionBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcCollectionCreate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.collection.create";

    public SvcCollectionCreate() {
        addToDefinition(defn);
    }

    static void addToDefinition(Interface defn) {
        defn.add(new Interface.Element("public", BooleanType.DEFAULT, "Is the collection public? Defaults to true.", 0,
                1));
        defn.add(new Interface.Element("featured", BooleanType.DEFAULT,
                "Is the collection featured? Defaults to false.", 0, 1));
        Interface.Element et = new Interface.Element("element_text", XmlDocType.DEFAULT, "Element text.", 1,
                Integer.MAX_VALUE);
        et.add(new Interface.Element("html", BooleanType.DEFAULT, "Is html? ", 0, 1));
        et.add(new Interface.Element("text", StringType.DEFAULT, "text.", 1, 1));
        et.add(new Interface.Element("element", LongType.POSITIVE_ONE, "element id", 1, 1));
        defn.add(et);
    }

    static CollectionBuilder parse(XmlDoc.Element args) throws Throwable {
        CollectionBuilder cb = new CollectionBuilder();
        cb.setPublic(args.booleanValue("public", true));
        cb.setFeatured(args.booleanValue("featured", false));
        List<XmlDoc.Element> ets = args.elements("element_text");
        if (ets != null) {
            for (XmlDoc.Element et : ets) {
                boolean html = et.booleanValue("html", false);
                String text = et.value("text");
                long elementId = et.longValue("element");
                cb.addElementText(html, text, elementId);
            }
        }
        return cb;
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        CollectionBuilder cb = parse(args);
        Collection c = omekaClient.createCollection(cb);
        OmekaXmlUtils.saveCollectionXml(c, w);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Creates an OMEKA collection on the specified OMEKA site.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
