package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.User;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcUserDescribe extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.user.describe";

    public SvcUserDescribe() {
        defn.add(new Interface.Element("id", LongType.POSITIVE, "The id of the OMEKA user.", 1, 1));
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        User user = omekaClient.getUser(id);
        OmekaXmlUtils.saveUserXml(user, w);
    }

    @Override
    public Access access() {
        return ACCESS_ACCESS;
    }

    @Override
    public String description() {
        return "Gets information about the specified user.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
