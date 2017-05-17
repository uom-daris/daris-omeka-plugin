package io.github.xtman.omeka.client.command.resource;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntityCommand;
import io.github.xtman.omeka.model.Resources;

public class GetResourcesCommand extends GetEntityCommand<Resources> {

    public GetResourcesCommand(OmekaClient client) {
        super(client, "resources");
    }

    @Override
    protected Resources instantiate(JSONObject jo) throws Throwable {
        return new Resources(jo);
    }

}
