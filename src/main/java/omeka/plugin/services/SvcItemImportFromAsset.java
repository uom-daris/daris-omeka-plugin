package omeka.plugin.services;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import arc.mf.plugin.ServiceExecutor;
import arc.mf.plugin.dtype.AssetType;
import arc.mf.plugin.dtype.EnumType;
import arc.mf.plugin.dtype.StringType;
import arc.xml.XmlDoc;
import arc.xml.XmlDocMaker;
import arc.xml.XmlWriter;
import io.github.xtman.http.exception.HttpException;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.model.Element;
import io.github.xtman.omeka.model.Item;
import io.github.xtman.omeka.model.Site;
import io.github.xtman.omeka.model.builder.ItemBuilder;
import omeka.plugin.util.AssetUtils;

public class SvcItemImportFromAsset extends OmekaPluginService {

    public static final String SERVICE_NAME = "omeka.item.import.from.asset";

    public static final String ITEM_IDENTITY_DOC_TYPE = "omeka:omeka-item-identity";

    public SvcItemImportFromAsset() {
        SvcItemCreate.addToDefinition(defn, true);
        defn.add(new Interface.Element("id", AssetType.DEFAULT, "The id of the source asset.", 0, Integer.MAX_VALUE));
        defn.add(new Interface.Element("where", StringType.DEFAULT, "Query to select the source assets.", 0, 1));
        defn.add(new Interface.Element("if-exists", new EnumType(new String[] { "ignore", "update" }),
                "Action to take if the item already exists. Defaults to ignore.", 0, 1));

    }

    static void importItemFromAsset(ServiceExecutor executor, String assetId, XmlDoc.Element args,
            OmekaClient omekaClient, String site, String itemTypeId, ResultSet<Element> elements,
            boolean ignoreIfExists, XmlWriter w) throws Throwable {

        XmlDoc.Element ae = AssetUtils.getAssetMeta(executor, assetId);
        assetId = ae.value("@id");
        XmlDoc.Element iie = ae.element("meta/" + ITEM_IDENTITY_DOC_TYPE);
        Item item = null;
        boolean exists = false;
        if (iie != null) {
            XmlDoc.Element itemElement = null;
            List<XmlDoc.Element> ies = iie.elements("item");
            if (ies != null) {
                for (XmlDoc.Element ie : ies) {
                    String url = ie.value("@url");
                    if (url != null && url.startsWith(omekaClient.endPoint())) {
                        itemElement = ie;
                        break;
                    }
                }
            }
            if (itemElement != null) {
                // get
                try {
                    item = omekaClient.getItem(itemElement.longValue());
                } catch (HttpException e) {
                    if (e.isNotFoundError()) {
                        // item is not found on OMEKA server.
                    } else {
                        throw e;
                    }
                }
                // already exists and ignore.
                if (item != null) {
                    exists = true;
                    if (ignoreIfExists) {
                        w.add("item", new String[] { "asset", assetId, "exists", Boolean.toString(exists), "updated",
                                "false" }, item.id());
                        return;
                    }
                }
            }

        }

        ItemBuilder ib = SvcItemCreate.parse(args, omekaClient);
        if (item != null) {
            // update item
            item = omekaClient.updateItem(item.id(), ib);
        } else {
            // create item
            item = omekaClient.createItem(ib);

            // update asset metadata
            XmlDocMaker dm = new XmlDocMaker("args");
            dm.push("meta", new String[] { "action", "replace" });
            dm.push(ITEM_IDENTITY_DOC_TYPE);
            if (iie != null) {
                List<XmlDoc.Element> ies = iie.elements("item");
                if (ies != null) {
                    for (XmlDoc.Element ie : ies) {
                        String url = ie.value("@url");
                        if (url != null && url.startsWith(omekaClient.endPoint())) {
                            continue;
                        }
                        dm.add(ie);
                    }
                }
            }
            dm.add("item", new String[] { "site", site, "collection",
                    item.collection() == null ? null : Long.toString(item.collection().id()), "url", item.url() },
                    item.id());
            dm.pop();
            dm.pop();
            dm.add("id", assetId);
            System.out.println(dm.root());
            executor.execute("asset.set", dm.root());
        }

        w.add("item", new String[] { "asset", assetId, "exists", Boolean.toString(exists), "updated", "true" },
                item.id());

        // create OMEKA file
        if (ae.elementExists("content")) {
            // TODO:
        }

    }

    @Override
    protected void execute(OmekaClient omekaClient, XmlDoc.Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        Set<String> assetIds = new LinkedHashSet<String>();
        if (args.elementExists("id")) {
            assetIds.addAll(args.values("id"));
        } else if (args.elementExists("where")) {
            XmlDocMaker dm = new XmlDocMaker("args");
            dm.add("where", args.value("where"));
            dm.add("size", "infinity");
            Collection<String> ids = executor().execute("asset.query", dm.root()).values("id");
            if (ids != null && !ids.isEmpty()) {
                assetIds.addAll(ids);
            }
        } else {
            throw new IllegalArgumentException("Missing argument: where or id.");
        }

        if (assetIds.isEmpty()) {
            // no input asset
            return;
        }

        boolean ignoreIfExists = "ignore".equalsIgnoreCase(args.stringValue("if-exists", "ignore"));
        String itemTypeId = null;
        if (args.elementExists("item_type")) {
            if (args.elementExists("item_type/id")) {
                itemTypeId = args.value("item_type/id");
            } else {
                if (!args.elementExists("item_type/name")) {
                    throw new IllegalArgumentException("Missing item_type/id or item_type/name.");
                }
                String itemTypeName = args.value("item_type/name");
                itemTypeId = Long.toString(SvcItemCreate.findItemTypeByName(omekaClient, itemTypeName));
            }
        }
        ResultSet<Element> elements = null;
        if (args.elementExists("element_text")) {
            elements = omekaClient.listElements(null);
        }
        Site site = omekaClient.getSite();
        String siteTitle = site == null ? null : site.title();

        for (String assetId : assetIds) {
            importItemFromAsset(executor(), assetId, args, omekaClient, siteTitle, itemTypeId, elements, ignoreIfExists,
                    w);
        }
    }

    @Override
    public Access access() {
        return ACCESS_MODIFY;
    }

    @Override
    public String description() {
        return "Import OMEKA items for the specified assets.";
    }

    @Override
    public String name() {
        return SERVICE_NAME;
    }

}