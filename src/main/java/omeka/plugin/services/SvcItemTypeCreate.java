package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.mf.plugin.dtype.StringType;
import arc.xml.XmlDoc;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.ItemType;
import io.github.xtman.omeka.model.builder.ItemTypeBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcItemTypeCreate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.item_type.create";

    public SvcItemTypeCreate() {
        addToDefinition(defn);
    }

    static void addToDefinition(Interface defn) {
        defn.add(new Interface.Element("name", StringType.DEFAULT, "Name of the item type.", 1, 1));
        defn.add(new Interface.Element("description", StringType.DEFAULT, "Description of the item type.", 0, 1));
        defn.add(new Interface.Element("element", LongType.POSITIVE_ONE, "element id.", 0, Integer.MAX_VALUE));
    }

    static ItemTypeBuilder parse(XmlDoc.Element args) throws Throwable {
        ItemTypeBuilder itb = new ItemTypeBuilder();
        if (args.elementExists("name")) {
            itb.setName(args.value("name"));
        }
        if (args.elementExists("description")) {
            itb.setDescription(args.value("description"));
        }
        if (args.elementExists("element")) {
            itb.setElements(args.longValues("element"));
        }
        return itb;
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        ItemTypeBuilder itb = parse(args);
        ItemType it = omekaClient.createItemType(itb);
        OmekaXmlUtils.saveItemTypeXml(it, w);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Creates a OMEKA item type.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
