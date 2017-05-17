package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.mf.plugin.dtype.StringType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.client.command.element.GetElementsCommand;
import io.github.xtman.omeka.model.Element;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcElementList extends OmekaEntityListPluginService<Element> {

    public static final String SERVICE_NAME = "omeka.element.list";

    public SvcElementList() {
        defn.add(new Interface.Element("element_set", LongType.POSITIVE_ONE, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("name", StringType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("item_type", LongType.POSITIVE_ONE, "Query parameter.", 0, 1));
    }

    @Override
    protected ResultSet<Element> listEntities(OmekaClient omekaClient, long pageIndex, int pageSize,
            XmlDoc.Element args) throws Throwable {
        GetElementsCommand.Params params = new GetElementsCommand.Params().setPage(pageIndex).setPerPage(pageSize);
        if (args.elementExists("element_set")) {
            params.setElementSet(args.longValue("element_set"));
        }
        if (args.elementExists("name")) {
            params.setName(args.value("name"));
        }
        if (args.elementExists("item_type")) {
            params.setItemType(args.longValue("item_type"));
        }
        return omekaClient.listElements(params);
    }

    @Override
    protected void describeEntity(Element element, XmlWriter w) throws Throwable {
        OmekaXmlUtils.saveElementXml(element, w);
    }

    @Override
    public String description() {
        return "List elements.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
