package omeka.plugin.services;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import arc.mf.plugin.ServiceExecutor;
import arc.mf.plugin.dtype.AssetType;
import arc.mf.plugin.dtype.EnumType;
import arc.mf.plugin.dtype.LongType;
import arc.mf.plugin.dtype.StringType;
import arc.xml.XmlDoc;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlDocMaker;
import arc.xml.XmlWriter;
import io.github.xtman.http.exception.HttpException;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.File;
import io.github.xtman.omeka.model.Site;
import io.github.xtman.omeka.model.builder.FileBuilder;
import omeka.plugin.util.AssetUtils;

public class SvcFileImportFromAsset extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.file.import.from.asset";

    public static final String FILE_IDENTITY_DOC_TYPE = "omeka:omeka-file-identity";

    public SvcFileImportFromAsset() {
        defn.add(new Interface.Element("item", LongType.POSITIVE_ONE, "item.", 1, 1));

        defn.add(new Interface.Element("id", AssetType.DEFAULT,
                "The source asset id. Asset without content will be ignored.", 0, Integer.MAX_VALUE));
        defn.add(new Interface.Element("where", StringType.DEFAULT,
                "Query to select the source assets. Asset without content will be ignored.", 0, 1));

        SvcFileCreate.addToDefinition(defn, true);

        defn.add(new Interface.Element("if-exists", new EnumType(new String[] { "ignore", "update" }),
                "Action to take if the file already exists in OMEKA. Defaults to ignore."));

    }

    @Override
    protected void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {

        Set<String> assetIds = new LinkedHashSet<String>();
        if (args.elementExists("id")) {
            assetIds.addAll(args.values("id"));
        } else if (args.elementExists("where")) {
            XmlDocMaker dm = new XmlDocMaker("args");
            dm.add("where", "(" + args.value("where") + ") and (asset has content)");
            dm.add("size", "infinity");
            Collection<String> ids = executor().execute("asset.query", dm.root()).values("id");
            if (ids != null && !ids.isEmpty()) {
                assetIds.addAll(ids);
            }
        } else {
            throw new IllegalArgumentException("Missing argument: where or id.");
        }

        Site site = omekaClient.getSite();
        String siteTitle = site == null ? null : site.title();
        boolean ignoreIfExists = "ignore".equalsIgnoreCase(args.stringValue("if-exists", "ignore"));

        long itemId = args.longValue("item");
        for (String assetId : assetIds) {
            importFileFromAsset(executor(), omekaClient, siteTitle, itemId, assetId, args, ignoreIfExists);
        }
    }

    private static void importFileFromAsset(ServiceExecutor executor, OmekaClient omekaClient, String site, long itemId,
            String assetId, XmlDoc.Element args, boolean ignoreIfExists) throws Throwable {
        XmlDoc.Element ae = AssetUtils.getAssetMeta(executor, assetId);
        assetId = ae.value("@id");
        if (!ae.elementExists("content")) {
            return;
        }
        XmlDoc.Element fie = ae.element("meta/" + FILE_IDENTITY_DOC_TYPE);
        File file = null;
        if (fie != null) {
            XmlDoc.Element fileElement = null;
            List<XmlDoc.Element> fes = fie.elements("file");
            if (fes != null) {
                for (XmlDoc.Element fe : fes) {
                    long item = fe.longValue("@item");
                    String url = fe.value("@url");
                    if (url != null && url.startsWith(omekaClient.endPoint()) && item == itemId) {
                        fileElement = fe;
                        break;
                    }
                }
            }
            if (fileElement != null) {
                // get
                try {
                    file = omekaClient.getFile(fileElement.longValue());
                } catch (HttpException e) {
                    if (e.isNotFoundError()) {
                        // item is not found on OMEKA server.
                    } else {
                        throw e;
                    }
                }
                // already exists
                if (file != null) {
                    if (ignoreIfExists) {
                        // ignore
                        return;
                    } else {
                        // delete existing file
                        omekaClient.deleteFile(file.id());
                    }
                }
            }
        }

        // create omeka file
        FileBuilder fb = SvcFileCreate.parse(omekaClient, args);
        SvcFileCreate.buildFileFromAsset(executor, fb, assetId);
        file = omekaClient.createFile(fb);

        // update asset metadata
        XmlDocMaker dm = new XmlDocMaker();
        dm.push("meta", new String[] { "action", "replace" });
        dm.push(FILE_IDENTITY_DOC_TYPE);
        List<XmlDoc.Element> fes = fie.elements("file");
        if (fes != null) {
            for (XmlDoc.Element fe : fes) {
                long item = fe.longValue("@item");
                String url = fe.value("@url");
                if (item == itemId && url != null && url.startsWith(omekaClient.endPoint())) {
                    continue;
                }
                dm.add(fe);
            }
        }
        dm.add("file", new String[] { "site", site, "item", Long.toString(itemId), "url", file.url() }, file.id());
        dm.pop();
        dm.pop();
        dm.add("id", assetId);
        executor.execute("asset.set", dm.root());

    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Import files for the given item from the specified assets.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}
