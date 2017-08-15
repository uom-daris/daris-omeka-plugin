package omeka.plugin.services;

import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.File;
import io.github.xtman.omeka.model.builder.FileBuilder;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcFileUpdate extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.file.update";

    public SvcFileUpdate() {
        defn.add(new Interface.Element("id", LongType.POSITIVE_ONE, "The id of the OMEKA file.", 1, 1));
        SvcFileCreate.addToDefinition(defn, false);
    }

    @Override
    protected void execute(OmekaClient omekaClient, XmlDoc.Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long id = args.longValue("id");
        FileBuilder fb = SvcFileCreate.parse(omekaClient, args);
        File f = omekaClient.updateFile(id, fb);
        OmekaXmlUtils.saveFileXml(f, w, true);
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Update the specified OMEKA file.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
