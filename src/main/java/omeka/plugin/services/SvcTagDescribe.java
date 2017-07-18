package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Tag;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcTagDescribe extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.tag.describe";

    public SvcTagDescribe() {
        defn.add(new Interface.Element("id", LongType.POSITIVE, "The id of the OMEKA tag.", 1, 1));
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        Tag tag = omekaClient.getTag(id);
        OmekaXmlUtils.saveTagXml(tag, w, true);

    }

    @Override
    public Access access() {
        return ACCESS_ACCESS;
    }

    @Override
    public String description() {
        return "Gets information about the specified tag";
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
