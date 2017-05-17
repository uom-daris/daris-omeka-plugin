package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Element;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcElementDescribe extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.element.describe";

    public SvcElementDescribe() {
        defn.add(new Interface.Element("id", LongType.POSITIVE, "The id of the OMEKA element.", 1, 1));
    }

    @Override
    protected void execute(OmekaClient omekaClient, XmlDoc.Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        Element e = omekaClient.getElement(id);
        OmekaXmlUtils.saveElementXml(e, w);
    }


    @Override
    public Access access() {
        return ACCESS_ACCESS;
    }

    @Override
    public String description() {
        return "Gets information about the specified element.";
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
