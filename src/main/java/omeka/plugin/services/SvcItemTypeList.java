package omeka.plugin.services;

import arc.mf.plugin.dtype.StringType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.client.command.itemtype.GetItemTypesCommand;
import io.github.xtman.omeka.model.ItemType;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcItemTypeList extends OmekaEntityListPluginService<ItemType> {

    public static final String SERVICE_NAME = "omeka.item_type.list";

    public SvcItemTypeList() {
        defn.add(new Interface.Element("name", StringType.DEFAULT, "Query parameter.", 0, 1));
    }

    @Override
    protected ResultSet<ItemType> listEntities(OmekaClient omekaClient, long pageIndex, int pageSize,
            XmlDoc.Element args) throws Throwable {
        GetItemTypesCommand.Params params = new GetItemTypesCommand.Params().setPage(pageIndex).setPerPage(pageSize);
        if (args.elementExists("name")) {
            params.setName(args.value("name"));
        }
        return omekaClient.listItemTypes(params);
    }

    @Override
    protected void describeEntity(ItemType itemType, XmlWriter w, boolean detail) throws Throwable {
        OmekaXmlUtils.saveItemTypeXml(itemType, w, detail);
    }

    @Override
    public String description() {
        return "List item types.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
