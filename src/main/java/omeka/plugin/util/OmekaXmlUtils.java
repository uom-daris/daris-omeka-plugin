package omeka.plugin.util;

import java.util.List;

import arc.xml.XmlDoc;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.util.OmekaDateUtils;
import io.github.xtman.omeka.model.Collection;
import io.github.xtman.omeka.model.Element;
import io.github.xtman.omeka.model.ElementSet;
import io.github.xtman.omeka.model.ElementText;
import io.github.xtman.omeka.model.Entity;
import io.github.xtman.omeka.model.EntityBase;
import io.github.xtman.omeka.model.EntityInfo;
import io.github.xtman.omeka.model.EntitySetInfo;
import io.github.xtman.omeka.model.File;
import io.github.xtman.omeka.model.Item;
import io.github.xtman.omeka.model.ItemType;
import io.github.xtman.omeka.model.Resources;
import io.github.xtman.omeka.model.Site;
import io.github.xtman.omeka.model.Tag;
import io.github.xtman.omeka.model.User;
import io.github.xtman.util.StringUtils;

public class OmekaXmlUtils {

    public static <T extends Entity> void saveXml(T entity, XmlWriter w) throws Throwable {
        if (entity instanceof EntityBase) {
            if (entity instanceof EntityInfo) {
                if (entity instanceof Tag) {
                    saveTagXml((Tag) entity, w);
                } else {
                    saveEntityInfoXml((EntityInfo) entity, w);
                }
            } else if (entity instanceof Collection) {
                saveCollectionXml((Collection) entity, w);
            } else if (entity instanceof Element) {
                saveElementXml((Element) entity, w);
            } else if (entity instanceof ElementSet) {
                saveElementSetXml((ElementSet) entity, w);
            } else if (entity instanceof File) {
                saveFileXml((File) entity, w);
            } else if (entity instanceof Item) {
                saveItemXml((Item) entity, w);
            } else if (entity instanceof ItemType) {
                saveItemTypeXml((ItemType) entity, w);
            } else if (entity instanceof User) {
                saveUserXml((User) entity, w);
            }
        } else if (entity instanceof Resources) {
            saveResourcesXml((Resources) entity, w);
        } else if (entity instanceof Site) {
            saveSiteXml((Site) entity, w);
        } else if (entity instanceof User) {
            saveUserXml((User) entity, w);
        }
    }

    public static void saveCollectionXml(Collection c, XmlWriter w) throws Throwable {
        w.push("collection", new String[] { "id", Long.toString(c.id()) });
        saveEntityBaseXml(c, w);
        w.add("public", c.isPublic());
        w.add("featured", c.isFeatured());
        if (c.added() != null) {
            w.add("added", OmekaDateUtils.formatDate(c.added()));
        }
        if (c.modified() != null) {
            w.add("modified", OmekaDateUtils.formatDate(c.modified()));
        }
        if (c.owner() != null) {
            w.push("owner");
            saveEntityInfoXml(c.owner(), w);
            w.pop();
        }
        if (c.items() != null) {
            w.push("items");
            saveEntitySetInfoXml(c.items(), w);
            w.pop();
        }
        List<ElementText> ets = c.elementTexts();
        if (ets != null && !ets.isEmpty()) {
            w.push("element_texts");
            for (ElementText et : ets) {
                saveElementTextXml(et, w);
            }
            w.pop();
        }
        w.pop();
    }

    public static void saveElementXml(Element e, XmlWriter w) throws Throwable {
        w.push("element", new String[] { "id", Long.toString(e.id()) });
        saveEntityBaseXml(e, w);
        if (e.order() != null) {
            w.add("order", e.order());
        }
        if (e.name() != null) {
            w.add("name", e.name());
        }
        if (e.description() != null) {
            w.add("description", e.description());
        }
        if (e.comment() != null) {
            w.add("comment", e.comment());
        }
        if (e.elementSet() != null) {
            w.push("element_set");
            saveEntityInfoXml(e.elementSet(), w);
            w.pop();
        }
        w.pop();
    }

    public static void saveTagXml(Tag t, XmlWriter w) throws Throwable {
        w.push("tag", new String[] { "id", Long.toString(t.id()) });
        saveEntityInfoXml(t, w);
        w.pop();
    }

    private static void saveEntityInfoXml(EntityInfo e, XmlWriter w) throws Throwable {
        saveEntityBaseXml(e, w);
        if (e.name() != null) {
            w.add("name", e.name());
        }
        if (e.resource() != null) {
            w.add("resource", e.resource());
        }
    }

    private static void saveEntitySetInfoXml(EntitySetInfo e, XmlWriter w) throws Throwable {
        w.add("count", e.count());
        if (e.url() != null) {
            w.add("url", e.url());
        }
        if (e.resource() != null) {
            w.add("resource", e.resource());
        }
    }

    private static void saveEntityBaseXml(EntityBase e, XmlWriter w) throws Throwable {
        w.add("id", e.id());
        if (e.url() != null) {
            w.add("url", e.url());
        }
    }

    public static void saveResourcesXml(Resources resources, XmlWriter w) throws Throwable {
        w.push("resources");
        saveResourceTypeXml("site", resources.site(), w);
        saveResourceTypeXml("resources", resources.resources(), w);
        saveResourceTypeXml("collections", resources.collections(), w);
        saveResourceTypeXml("items", resources.items(), w);
        saveResourceTypeXml("files", resources.files(), w);
        saveResourceTypeXml("item_types", resources.itemTypes(), w);
        saveResourceTypeXml("elements", resources.elements(), w);
        saveResourceTypeXml("element_sets", resources.elementSets(), w);
        saveResourceTypeXml("users", resources.users(), w);
        saveResourceTypeXml("tags", resources.tags(), w);
        w.pop();
    }

    public static void saveSiteXml(Site site, XmlWriter w) throws Throwable {
        w.push("site");
        w.add("omeka_url", site.omekaUrl());
        w.add("omeka_version", site.omekaVersion());
        if (site.title() != null) {
            w.add("title", site.title());
        }
        if (site.author() != null) {
            w.add("author", site.author());
        }
        if (site.copyright() != null) {
            w.add("copyright", site.copyright());
        }
        w.pop();
    }

    public static void saveUserXml(User user, XmlWriter w) throws Throwable {
        w.push("user", new String[] { "id", Long.toString(user.id()) });
        saveEntityBaseXml(user, w);
        w.add("active", user.isActive());
        if (user.role() != null) {
            w.add("role", user.role());
        }
        if (user.username() != null) {
            w.add("username", user.username());
        }
        if (user.name() != null) {
            w.add("name", user.name());
        }
        if (user.email() != null) {
            w.add("email", user.email());
        }
        w.pop();
    }

    private static void saveResourceTypeXml(String typeName, Resources.TypeInfo typeInfo, XmlWriter w)
            throws Throwable {
        if (typeInfo != null) {
            w.push(typeName);
            if (typeInfo.controller() != null) {
                w.add("controller", typeInfo.controller());
            }
            if (typeInfo.recordType() != null) {
                w.add("record_type", typeInfo.recordType());
            }
            if (typeInfo.actions() != null && !typeInfo.actions().isEmpty()) {
                w.add("actions", StringUtils.join(typeInfo.actions(), ","));
            }
            if (typeInfo.indexParams() != null && !typeInfo.indexParams().isEmpty()) {
                w.add("index_params", StringUtils.join(typeInfo.indexParams(), ","));
            }
            if (typeInfo.url() != null) {
                w.add("url", typeInfo.url());
            }
            w.pop();
        }
    }

    private static void saveElementTextXml(ElementText et, XmlWriter w) throws Throwable {
        w.push("element_text");
        w.add("html", et.isHtml());
        if (et.text() != null) {
            w.add("text", et.text());
        }
        if (et.elementSet() != null) {
            w.push("element_set");
            saveEntityInfoXml(et.elementSet(), w);
            w.pop();
        }
        if (et.element() != null) {
            w.push("element");
            saveEntityInfoXml(et.element(), w);
            w.pop();
        }
        w.pop();
    }

    public static void saveElementSetXml(ElementSet es, XmlWriter w) throws Throwable {
        w.push("element_set", new String[] { "id", Long.toString(es.id()) });
        saveEntityBaseXml(es, w);
        if (es.name() != null) {
            w.add("name", es.name());
        }
        if (es.description() != null) {
            w.add("description", es.name());
        }
        if (es.recordType() != null) {
            w.add("record_type", es.recordType());
        }
        if (es.elements() != null) {
            w.push("elements");
            saveEntitySetInfoXml(es.elements(), w);
            w.pop();
        }
        w.pop();
    }

    public static void saveItemXml(Item i, XmlWriter w) throws Throwable {
        w.push("item", new String[] { "id", Long.toString(i.id()) });
        saveEntityBaseXml(i, w);
        w.add("public", i.isPublic());
        w.add("featured", i.isFeatured());
        if (i.added() != null) {
            w.add("added", OmekaDateUtils.formatDate(i.added()));
        }
        if (i.modified() != null) {
            w.add("modified", OmekaDateUtils.formatDate(i.modified()));
        }
        if (i.itemType() != null) {
            w.push("item_type");
            saveEntityInfoXml(i.itemType(), w);
            w.pop();
        }
        if (i.collection() != null) {
            w.push("collection");
            saveEntityInfoXml(i.collection(), w);
            w.pop();
        }
        if (i.owner() != null) {
            w.push("owner");
            saveEntityInfoXml(i.owner(), w);
            w.pop();
        }
        if (i.files() != null) {
            w.push("files");
            saveEntitySetInfoXml(i.files(), w);
            w.pop();
        }
        List<EntityInfo> tags = i.tags();
        if (tags != null && !tags.isEmpty()) {
            w.push("tags");
            for (EntityInfo tag : tags) {
                w.push("tag");
                saveEntityInfoXml(tag, w);
                w.pop();
            }
            w.pop();
        }
        List<ElementText> ets = i.elementTexts();
        if (ets != null && !ets.isEmpty()) {
            w.push("element_texts");
            for (ElementText et : ets) {
                saveElementTextXml(et, w);
            }
            w.pop();
        }
        w.pop();
    }

    public static void saveItemTypeXml(ItemType it, XmlWriter w) throws Throwable {
        w.push("item", new String[] { "id", Long.toString(it.id()) });
        saveEntityBaseXml(it, w);
        if (it.name() != null) {
            w.add("name", it.name());
        }
        if (it.description() != null) {
            w.add("description", it.description());
        }
        List<EntityBase> elements = it.elements();
        if (elements != null && !elements.isEmpty()) {
            w.push("elements");
            for (EntityBase element : elements) {
                w.push("element");
                saveEntityBaseXml(element, w);
                w.pop();
            }
            w.pop();
        }
        if (it.items() != null) {
            w.push("items");
            saveEntitySetInfoXml(it.items(), w);
            w.pop();
        }
        w.pop();
    }

    public static void saveFileXml(File f, XmlWriter w) throws Throwable {
        w.push("file", new String[] { "id", Long.toString(f.id()) });
        saveEntityBaseXml(f, w);
        saveFileUrlsXml(f.fileUrls(), w);
        if (f.added() != null) {
            w.add("added", OmekaDateUtils.formatDate(f.added()));
        }
        if (f.modified() != null) {
            w.add("modified", OmekaDateUtils.formatDate(f.modified()));
        }
        if (f.filename() != null) {
            w.add("filename", f.filename());
        }
        if (f.authentication() != null) {
            w.add("authentication", f.authentication());
        }
        w.add("has_derivative_image", f.hasDerivativeImage());
        if (f.mimeType() != null) {
            w.add("mime_type", f.mimeType());
        }
        if (f.order() != null) {
            w.add("order", f.order());
        }
        if (f.originalFilename() != null) {
            w.add("original_filename", f.originalFilename());
        }
        w.add("size", f.size());
        w.add("stored", f.stored());
        if (f.typeOS() != null) {
            w.add("type_os", f.typeOS());
        }
        if (f.metadata() != null) {
            XmlDoc.Element me = new XmlDoc().parse("<metadata>" + org.json.XML.toString(f.metadata()) + "</metadata>");
            w.add(me);
        }
        if (f.item() != null) {
            w.push("item");
            saveEntityInfoXml(f.item(), w);
            w.pop();
        }
        List<ElementText> ets = f.elementTexts();
        if (ets != null && !ets.isEmpty()) {
            w.push("element_texts");
            for (ElementText et : ets) {
                saveElementTextXml(et, w);
            }
            w.pop();
        }
        w.pop();
    }

    private static void saveFileUrlsXml(File.FileUrls urls, XmlWriter w) throws Throwable {
        if (urls != null) {
            w.push("file_urls");
            if (urls.original != null) {
                w.add("original", urls.original);
            }
            if (urls.fullsize != null) {
                w.add("fullsize", urls.fullsize);
            }
            if (urls.thumbnail != null) {
                w.add("thumbnail", urls.thumbnail);
            }
            if (urls.squareThumbnail != null) {
                w.add("square_thumbnail", urls.squareThumbnail);
            }
            w.pop();
        }
    }

}
