package io.github.xtman.omeka.client.command.elementset;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PostEntityCommand;
import io.github.xtman.omeka.model.ElementSet;
import io.github.xtman.omeka.model.builder.ElementSetBuilder;

public class PostElementSetCommand extends PostEntityCommand<ElementSet> {

    public PostElementSetCommand(OmekaClient client, ElementSetBuilder elementSet) {
        super(client, "element_sets", elementSet);
    }

    @Override
    protected ElementSet instantiate(JSONObject jo) throws Throwable {
        return new ElementSet(jo);
    }

}
