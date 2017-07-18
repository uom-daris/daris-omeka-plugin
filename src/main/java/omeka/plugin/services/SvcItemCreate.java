package omeka.plugin.services;

import java.util.ArrayList;
import java.util.List;

import arc.mf.plugin.dtype.BooleanType;
import arc.mf.plugin.dtype.LongType;
import arc.mf.plugin.dtype.StringType;
import arc.mf.plugin.dtype.XmlDocType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.model.Element;
import io.github.xtman.omeka.model.Item;
import io.github.xtman.omeka.model.builder.ItemBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcItemCreate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.item.create";

    public SvcItemCreate() {
        addToDefinition(defn);
    }

    static void addToDefinition(Interface defn) {
        defn.add(new Interface.Element("item_type", LongType.POSITIVE_ONE, "Item type id.", 0, 1));
        defn.add(new Interface.Element("collection", LongType.POSITIVE_ONE, "Collection id.", 0, 1));
        defn.add(new Interface.Element("public", BooleanType.DEFAULT, "Is public? Defaults to true.", 0, 1));
        defn.add(new Interface.Element("featured", BooleanType.DEFAULT, "Is featured? Defaults to false.", 0, 1));
        defn.add(new Interface.Element("tag", StringType.DEFAULT, "Tag.", 0, Integer.MAX_VALUE));
        Interface.Element et = new Interface.Element("element_text", XmlDocType.DEFAULT, "Element text.", 0,
                Integer.MAX_VALUE);
        et.add(new Interface.Element("html", BooleanType.DEFAULT, "Is html? ", 0, 1));
        et.add(new Interface.Element("text", StringType.DEFAULT, "text.", 1, 1));

        Interface.Element e = new Interface.Element("element", XmlDocType.DEFAULT, "element.", 1, 1);
        e.add(new Interface.Element("id", LongType.POSITIVE_ONE, "element id", 0, 1));
        Interface.Element en = new Interface.Element("name", StringType.DEFAULT, "element name", 0, 1);
        en.add(new Interface.Attribute("element_set", LongType.POSITIVE_ONE, "element set id", 0));
        e.add(en);
        et.add(e);

        defn.add(et);
    }

    static ItemBuilder parse(XmlDoc.Element args, OmekaClient omekaClient) throws Throwable {
        ItemBuilder ib = new ItemBuilder();
        if (args.elementExists("item_type")) {
            ib.setItemType(args.longValue("item_type"));
        }
        if (args.elementExists("collection")) {
            ib.setCollection(args.longValue("collection"));
        }
        ib.setPublic(args.booleanValue("public", true));
        ib.setFeatured(args.booleanValue("featured", false));
        java.util.Collection<String> tags = args.values("tag");
        if (tags != null && !tags.isEmpty()) {
            for (String tag : tags) {
                ib.addTag(tag);
            }
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
                    Long elementSetId = et.longValue("element/name/@element_set", null);
                    if (elements == null) {
                        elements = omekaClient.listElements(null);
                        if (elements == null || elements.isEmpty()) {
                            throw new Exception("No element is returned from OMEKA server.");
                        }
                    }
                    elementId = findElementByName(elements, elementName, elementSetId);
                }
                ib.addElementText(html, text, elementId);
            }
        }
        return ib;
    }

    private static long findElementByName(ResultSet<Element> elements, String elementName, Long elementSetId)
            throws Throwable {

        List<Element> es = elements.entities();
        List<Element> found = new ArrayList<Element>();
        for (Element e : es) {
            if (elementName.equals(e.name())) {
                if (elementSetId == null || elementSetId.equals(e.elementSet().id())) {
                    found.add(e);
                }
            }
        }
        if (found.isEmpty()) {
            throw new IllegalArgumentException("No '" + elementName + "' element is found.");
        } else {
            if (found.size() > 1) {
                StringBuilder err = new StringBuilder(
                        "Ambiguous element specification. Multiple '" + elementName + "' elements are found.");
                if (elementSetId == null) {
                    err.append(" Please specify element-text/element/name/@element-set.");
                } else {
                    err.append(" Please specify element-text/element/id instead.");
                }
                throw new IllegalArgumentException(err.toString());
            } else {
                return found.get(0).id();
            }
        }
    }

    @Override
    protected void execute(OmekaClient omekaClient, XmlDoc.Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        ItemBuilder ib = parse(args, omekaClient);
        Item i = omekaClient.createItem(ib);
        OmekaXmlUtils.saveItemXml(i, w, true);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Creates an OMEKA item.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
