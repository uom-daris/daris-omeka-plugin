package omeka.plugin.services;

import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Resources;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcResourcesDescribe extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.resources.describe";

    @Override
    protected boolean requiresApiKey() {
        return false;
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {

        Resources resources = omekaClient.getResources();
        OmekaXmlUtils.saveResourcesXml(resources, w);

    }

    @Override
    public Access access() {
        return ACCESS_ACCESS;
    }

    @Override
    public String description() {
        return "Describes available API resources and information about them";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
