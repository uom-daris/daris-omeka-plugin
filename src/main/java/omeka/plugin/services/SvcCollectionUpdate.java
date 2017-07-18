package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Collection;
import io.github.xtman.omeka.model.builder.CollectionBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcCollectionUpdate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.collection.update";

    public SvcCollectionUpdate() {
        defn.add(new Interface.Element("id", LongType.POSITIVE_ONE, "The id of the OMEKA collection.", 1, 1));
        SvcCollectionCreate.addToDefinition(defn);
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        CollectionBuilder cb = SvcCollectionCreate.parse(args);
        Collection c = omekaClient.updateCollection(id, cb);
        OmekaXmlUtils.saveCollectionXml(c, w, true);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Update the specified OMEKA collection.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
