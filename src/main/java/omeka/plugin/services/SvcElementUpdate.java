package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Element;
import io.github.xtman.omeka.model.builder.ElementBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcElementUpdate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.element.update";

    public SvcElementUpdate() {
        defn.add(new Interface.Element("id", LongType.POSITIVE_ONE, "The id of the OMEKA element.", 1, 1));
        SvcElementCreate.addToDefinition(defn);
    }

    @Override
    protected void execute(OmekaClient omekaClient, XmlDoc.Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        ElementBuilder eb = SvcElementCreate.parse(args);
        Element e = omekaClient.updateElement(id, eb);
        OmekaXmlUtils.saveElementXml(e, w);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Update the specified OMEKA element.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
