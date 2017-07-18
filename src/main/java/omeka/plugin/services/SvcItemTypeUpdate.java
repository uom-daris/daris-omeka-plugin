package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.ItemType;
import io.github.xtman.omeka.model.builder.ItemTypeBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcItemTypeUpdate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.item_type.update";

    public SvcItemTypeUpdate() {
        defn.add(new Interface.Element("id", LongType.POSITIVE_ONE, "The id of the OMEKA item type.", 1, 1));
        SvcItemTypeCreate.addToDefinition(defn);
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        ItemTypeBuilder itb = SvcItemTypeCreate.parse(args);
        ItemType it = omekaClient.updateItemType(id, itb);
        OmekaXmlUtils.saveItemTypeXml(it, w, true);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Update the specified OMEKA item type.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
