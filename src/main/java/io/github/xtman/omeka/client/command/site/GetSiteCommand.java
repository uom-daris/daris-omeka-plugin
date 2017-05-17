package io.github.xtman.omeka.client.command.site;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntityCommand;
import io.github.xtman.omeka.model.Site;

public class GetSiteCommand extends GetEntityCommand<Site> {

    public GetSiteCommand(OmekaClient client) {
        super(client, "site");
    }

    @Override
    protected Site instantiate(JSONObject jo) throws Throwable {
        return new Site(jo);
    }

}
