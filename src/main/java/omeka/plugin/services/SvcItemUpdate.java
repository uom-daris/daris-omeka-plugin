package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Item;
import io.github.xtman.omeka.model.builder.ItemBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcItemUpdate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.item.update";

    public SvcItemUpdate() {
        defn.add(new Interface.Element("id", LongType.POSITIVE_ONE, "The id of the OMEKA item.", 1, 1));
        SvcItemCreate.addToDefinition(defn, false);
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        Item item = updateItem(omekaClient, id, args);
        OmekaXmlUtils.saveItemXml(item, w, true);
    }

    static Item updateItem(OmekaClient omekaClient, long id, XmlDoc.Element args) throws Throwable {
        ItemBuilder ib = SvcItemCreate.parse(args, omekaClient);
        return omekaClient.updateItem(id, ib);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Update the specified OMEKA item.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
