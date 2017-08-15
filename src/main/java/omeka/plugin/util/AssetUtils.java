package omeka.plugin.util;

import arc.mf.plugin.ServiceExecutor;
import arc.xml.XmlDoc;
import arc.xml.XmlDocMaker;

public class AssetUtils {

    public static XmlDoc.Element getAssetMeta(ServiceExecutor executor, String id) throws Throwable {
        XmlDocMaker dm = new XmlDocMaker("args");
        dm.add("id", id);
        return executor.execute("asset.get", dm.root()).element("asset");
    }

}
