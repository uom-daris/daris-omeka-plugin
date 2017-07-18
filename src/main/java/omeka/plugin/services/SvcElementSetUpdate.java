package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.ElementSet;
import io.github.xtman.omeka.model.builder.ElementSetBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcElementSetUpdate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.element_set.update";

    public SvcElementSetUpdate() {
        defn.add(new Interface.Element("id", LongType.POSITIVE_ONE, "The id of the OMEKA element set.", 1, 1));
        SvcElementSetCreate.addToDefinition(defn);
    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        ElementSetBuilder esb = SvcElementSetCreate.parse(args);
        ElementSet es = omekaClient.updateElementSet(id, esb);
        OmekaXmlUtils.saveElementSetXml(es, w, true);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Update the specified OMEKA element set.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
