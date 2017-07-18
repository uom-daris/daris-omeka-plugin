package omeka.plugin.services;

import arc.mf.plugin.dtype.BooleanType;
import arc.mf.plugin.dtype.DateType;
import arc.mf.plugin.dtype.LongType;
import arc.mf.plugin.dtype.StringType;
import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.client.command.file.GetFilesCommand;
import io.github.xtman.omeka.model.File;
import omeka.plugin.util.OmekaXmlUtils;

public class SvcFileList extends OmekaEntityListPluginService<File> {

    public static final String SERVICE_NAME = "omeka.file.list";

    public SvcFileList() {
        defn.add(new Interface.Element("item", LongType.POSITIVE_ONE, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("order", LongType.POSITIVE_ONE, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("size_greater_than", LongType.POSITIVE_ONE, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("has_derivative_image", BooleanType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("mime_type", StringType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("added_since", DateType.DEFAULT, "Query parameter.", 0, 1));
        defn.add(new Interface.Element("modified_since", DateType.DEFAULT, "Query parameter.", 0, 1));
    }

    @Override
    protected ResultSet<File> listEntities(OmekaClient omekaClient, long pageIndex, int pageSize, XmlDoc.Element args)
            throws Throwable {
        GetFilesCommand.Params params = new GetFilesCommand.Params().setPage(pageIndex).setPerPage(pageSize);
        if (args.elementExists("item")) {
            params.setItem(args.longValue("item"));
        }
        if (args.elementExists("order")) {
            params.setOrder(args.longValue("order"));
        }
        if (args.elementExists("size_greater_than")) {
            params.setSizeGreaterThan(args.longValue("size_greater_than"));
        }
        if (args.elementExists("has_derivative_image")) {
            params.setHasDerivativeImage(args.booleanValue("has_derivative_image"));
        }
        if (args.elementExists("mime_type")) {
            params.setMimeType(args.value("mime_type"));
        }
        if (args.elementExists("added_since")) {
            params.setAddedSince(args.dateValue("added_since"));
        }
        if (args.elementExists("modified_since")) {
            params.setModifiedSince(args.dateValue("modified_since"));
        }
        return omekaClient.listFiles(params);
    }

    @Override
    protected void describeEntity(File file, XmlWriter w, boolean detail) throws Throwable {
        OmekaXmlUtils.saveFileXml(file, w, detail);
    }

    @Override
    public String description() {
        return "List files.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
