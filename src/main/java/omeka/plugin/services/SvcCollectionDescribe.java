package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Collection;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcCollectionDescribe extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.collection.describe";

    public SvcCollectionDescribe() {
        defn.add(new Interface.Element("id", LongType.POSITIVE, "The id of the OMEKA collection.", 1, 1));
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        Collection collection = omekaClient.getCollection(id);
        OmekaXmlUtils.saveCollectionXml(collection, w, true);
    }


    @Override
    public Access access() {
        return ACCESS_ACCESS;
    }

    @Override
    public String description() {
        return "Gets information about the specified collection.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

    @Override
    protected boolean requiresApiKey() {
        return false;
    }
}
