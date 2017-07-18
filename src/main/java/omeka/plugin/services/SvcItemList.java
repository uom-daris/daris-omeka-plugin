package omeka.plugin.services;

import arc.mf.plugin.dtype.BooleanType;
import arc.mf.plugin.dtype.DateType;
import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.client.command.item.GetItemsCommand;
import io.github.xtman.omeka.model.Item;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcItemList extends OmekaEntityListPluginService<Item> {

    public static final String SERVICE_NAME = "omeka.item.list";

    public SvcItemList() {
        defn.add(new Interface.Element("collection", LongType.POSITIVE_ONE, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("item_type", LongType.POSITIVE_ONE, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("featured", BooleanType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("public", BooleanType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("added_since", DateType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("modified_since", DateType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("owner", LongType.POSITIVE_ONE, "Query parameter.", 0, 1));
    }

    @Override
    protected ResultSet<Item> listEntities(OmekaClient omekaClient, long pageIndex, int pageSize, XmlDoc.Element args)
            throws Throwable {
        GetItemsCommand.Params params = new GetItemsCommand.Params().setPage(pageIndex).setPerPage(pageSize);
        if (args.elementExists("collection")) {
            params.setCollection(args.longValue("collection"));
        }
        if (args.elementExists("item_type")) {
            params.setCollection(args.longValue("item_type"));
        }
        if (args.elementExists("featured")) {
            params.setFeatured(args.booleanValue("featured"));
        }
        if (args.elementExists("public")) {
            params.setPublic(args.booleanValue("public"));
        }
        if (args.elementExists("added_since")) {
            params.setAddedSince(args.dateValue("added_since"));
        }
        if (args.elementExists("modified_since")) {
            params.setModifiedSince(args.dateValue("modified_since"));
        }
        if (args.elementExists("owner")) {
            params.setOwner(args.longValue("owner"));
        }
        return omekaClient.listItems(params);
    }

    @Override
    protected void describeEntity(Item item, XmlWriter w, boolean detail) throws Throwable {
        OmekaXmlUtils.saveItemXml(item, w, detail);
    }

    @Override
    public String description() {
        return "List items.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
