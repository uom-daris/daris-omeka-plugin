package omeka.plugin.services;

import arc.mf.plugin.PluginService;
import arc.mf.plugin.dtype.StringType;
import arc.mf.plugin.dtype.UrlType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.OmekaClientImpl;

public abstract class OmekaPluginService extends PluginService {

    protected Interface defn;

    protected OmekaPluginService() {
        defn = new Interface();
        defn.add(new Interface.Element("endpoint", UrlType.DEFAULT, "OMEKA site API endpoint address.", 1, 1));
        defn.add(new Interface.Element("api-key", StringType.DEFAULT, "OMEKA user's API key.", 0, 1));
    }

    protected boolean requiresApiKey() {
        return true;
    }

    @Override
    public Interface definition() {
        return this.defn;
    }

    @Override
    public final void execute(Element args, Inputs inputs, Outputs outputs, XmlWriter w) throws Throwable {
        String endpoint = args.value("endpoint");
        String apiKey = args.value("api-key");
        if (requiresApiKey() && apiKey == null) {
            throw new IllegalArgumentException("Missing api-key.");
        }
        OmekaClient omekaClient = new OmekaClientImpl(endpoint, apiKey);
        execute(omekaClient, args, inputs, outputs, w);
    }

    protected abstract void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable;

}
