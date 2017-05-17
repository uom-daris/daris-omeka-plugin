package omeka.plugin.services;

import arc.mf.plugin.dtype.BooleanType;
import arc.mf.plugin.dtype.DateType;
import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.client.command.collection.GetCollectionsCommand;
import io.github.xtman.omeka.model.Collection;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcCollectionList extends OmekaEntityListPluginService<Collection> {

    public static final String SERVICE_NAME = "omeka.collection.list";

    public SvcCollectionList() {
        defn.add(new Interface.Element("public", BooleanType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("featured", BooleanType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("added_since", DateType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("modified_since", DateType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("owner", LongType.POSITIVE_ONE, "Query parameter.", 0, 1));
    }

    @Override
    protected ResultSet<Collection> listEntities(OmekaClient omekaClient, long pageIndex, int pageSize,
            XmlDoc.Element args) throws Throwable {
        GetCollectionsCommand.Params params = new GetCollectionsCommand.Params().setPage(pageIndex)
                .setPerPage(pageSize);
        if (args.elementExists("public")) {
            params.setPublic(args.booleanValue("public"));
        }
        if (args.elementExists("featured")) {
            params.setFeatured(args.booleanValue("featured"));
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
        return omekaClient.listCollections(params);
    }

    @Override
    protected void describeEntity(Collection collection, XmlWriter w) throws Throwable {
        OmekaXmlUtils.saveCollectionXml(collection, w);
    }

    @Override
    public String description() {
        return "List collections.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
