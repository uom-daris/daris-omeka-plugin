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
import io.github.xtman.omeka.model.ItemType;
import io.github.xtman.omeka.model.builder.ItemBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcItemCreate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.item.create";

    public SvcItemCreate() {
        addToDefinition(defn, false);
    }

    static void addToDefinition(Interface defn, boolean importFromAsset) {
        Interface.Element it = new Interface.Element("item_type", XmlDocType.DEFAULT, "Item type.", 0, 1);
        it.add(new Interface.Element("id", LongType.POSITIVE_ONE,
                "Item type id. Either id or name of the item_type must be specified.", 0, 1));
        it.add(new Interface.Element("name", StringType.DEFAULT,
                "Item type name. Either id or name of the item_type must be specified.", 0, 1));
        defn.add(it);

        defn.add(new Interface.Element("collection", LongType.POSITIVE_ONE, "Collection id.", 0, 1));
        defn.add(new Interface.Element("public", BooleanType.DEFAULT, "Is public? Defaults to true.", 0, 1));
        defn.add(new Interface.Element("featured", BooleanType.DEFAULT, "Is featured? Defaults to false.", 0, 1));
        defn.add(new Interface.Element("tag", StringType.DEFAULT, "Tag.", 0, Integer.MAX_VALUE));
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

    static ItemBuilder parse(XmlDoc.Element args, OmekaClient omekaClient) throws Throwable {
        ItemBuilder ib = new ItemBuilder();
        if (args.elementExists("item_type")) {
            long itemTypeId;
            if (args.elementExists("item_type/id")) {
                itemTypeId = args.longValue("item_type/id");
            } else {
                if (!args.elementExists("item_type/name")) {
                    throw new IllegalArgumentException("Missing item_type/id or item_type/name.");
                }
                String itemTypeName = args.value("item_type/name");
                itemTypeId = findItemTypeByName(omekaClient, itemTypeName);
            }
            ib.setItemType(itemTypeId);
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
                    elementId = findElementByName(elements, elementName, elementSetId);
                }
                ib.addElementText(html, text, elementId);
            }
        }
        return ib;
    }

    static long findItemTypeByName(OmekaClient omekaClient, String itemTypeName) throws Throwable {
        ResultSet<ItemType> itemTypes = omekaClient.listItemTypes(null);
        if (itemTypes != null && !itemTypes.isEmpty()) {
            List<ItemType> its = itemTypes.entities();
            for (ItemType it : its) {
                if (itemTypeName.equals(it.name())) {
                    return it.id();
                }
            }
        }
        throw new IllegalArgumentException("No item_type '" + itemTypeName + "' is found.");
    }

    static long findElementByName(ResultSet<Element> elements, String elementName, Long elementSetId) throws Throwable {

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
                    err.append(" Please specify element_text/element/name/@element_set.");
                } else {
                    err.append(" Please specify element_text/element/id instead.");
                }
                throw new IllegalArgumentException(err.toString());
            } else {
                return found.get(0).id();
            }
        }
    }

    static Item createItem(OmekaClient omekaClient, XmlDoc.Element args) throws Throwable {
        ItemBuilder ib = parse(args, omekaClient);
        return omekaClient.createItem(ib);
    }

    @Override
    protected void execute(OmekaClient omekaClient, XmlDoc.Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        Item i = createItem(omekaClient, args);
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
