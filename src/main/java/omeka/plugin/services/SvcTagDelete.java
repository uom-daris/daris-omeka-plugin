package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;

public class SvcTagDelete extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.tag.delete";

    public SvcTagDelete() {
        defn.add(new Interface.Element("id", LongType.POSITIVE, "The id of the OMEKA tag.", 1, 1));
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        omekaClient.deleteTag(id);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Delete a tag.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
