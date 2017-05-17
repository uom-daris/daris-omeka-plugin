package io.github.xtman.omeka.client.command.elementset;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntityCommand;
import io.github.xtman.omeka.model.ElementSet;

public class GetElementSetCommand extends GetEntityCommand<ElementSet> {

    public GetElementSetCommand(OmekaClient client, long id) {
        super(client, "element_sets", id);
    }

    @Override
    protected ElementSet instantiate(JSONObject jo) throws Throwable {
        return new ElementSet(jo);
    }

}
