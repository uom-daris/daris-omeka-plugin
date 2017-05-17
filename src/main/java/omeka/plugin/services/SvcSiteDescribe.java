package omeka.plugin.services;

import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Site;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcSiteDescribe extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.site.describe";

    @Override
    protected boolean requiresApiKey() {
        return false;
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {

        Site site = omekaClient.getSite();
        OmekaXmlUtils.saveSiteXml(site, w);
    }

    @Override
    public Access access() {

        return ACCESS_ACCESS;
    }

    @Override
    public String description() {

        return "Gets information about the Omeka site.";
    }

    @Override
    public String name() {

        return SERVICE_NAME;
    }

}
