package omeka.plugin.services;

import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.client.command.tag.GetTagsCommand;
import io.github.xtman.omeka.model.Tag;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcTagList extends OmekaEntityListPluginService<Tag> {

    public static final String SERVICE_NAME = "omeka.tag.list";

    @Override
    protected ResultSet<Tag> listEntities(OmekaClient omekaClient, long pageIndex, int pageSize, XmlDoc.Element args)
            throws Throwable {
        GetTagsCommand.Params params = new GetTagsCommand.Params().setPage(pageIndex).setPerPage(pageSize);
        return omekaClient.listTags(params);
    }

    @Override
    protected void describeEntity(Tag tag, XmlWriter w) throws Throwable {
        OmekaXmlUtils.saveTagXml(tag, w);
    }

    @Override
    public String description() {
        return "List tags.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
