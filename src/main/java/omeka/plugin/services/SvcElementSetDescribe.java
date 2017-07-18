package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.ElementSet;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcElementSetDescribe extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.element_set.describe";

    public SvcElementSetDescribe() {
        defn.add(new Interface.Element("id", LongType.POSITIVE, "The id of the OMEKA element set.", 1, 1));
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        ElementSet es = omekaClient.getElementSet(id);
        OmekaXmlUtils.saveElementSetXml(es, w, true);
    }


    @Override
    public Access access() {
        return ACCESS_ACCESS;
    }

    @Override
    public String description() {
        return "Gets information about the specified element set.";
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
