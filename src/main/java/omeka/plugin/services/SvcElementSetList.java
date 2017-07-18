package omeka.plugin.services;

import arc.mf.plugin.dtype.StringType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.client.command.elementset.GetElementSetsCommand;
import io.github.xtman.omeka.model.ElementSet;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcElementSetList extends OmekaEntityListPluginService<ElementSet> {

    public static final String SERVICE_NAME = "omeka.element_set.list";

    public SvcElementSetList() {
        defn.add(new Interface.Element("name", StringType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("record_type", StringType.DEFAULT, "Query parameter.", 0, 1));
    }

    @Override
    protected ResultSet<ElementSet> listEntities(OmekaClient omekaClient, long pageIndex, int pageSize,
            XmlDoc.Element args) throws Throwable {
        GetElementSetsCommand.Params params = new GetElementSetsCommand.Params().setPage(pageIndex)
                .setPerPage(pageSize);
        if (args.elementExists("name")) {
            params.setName(args.value("name"));
        }
        if (args.elementExists("record_type")) {
            params.setRecordType(args.value("record_type"));
        }
        return omekaClient.listElementSets(params);
    }

    @Override
    protected void describeEntity(ElementSet es, XmlWriter w, boolean detail) throws Throwable {
        OmekaXmlUtils.saveElementSetXml(es, w, detail);
    }

    @Override
    public String description() {
        return "List element sets.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
