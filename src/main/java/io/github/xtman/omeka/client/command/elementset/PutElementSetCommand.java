package io.github.xtman.omeka.client.command.elementset;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PutEntityCommand;
import io.github.xtman.omeka.model.ElementSet;
import io.github.xtman.omeka.model.builder.ElementSetBuilder;

public class PutElementSetCommand extends PutEntityCommand<ElementSet> {

    public PutElementSetCommand(OmekaClient client, long id, ElementSetBuilder elementSet) {
        super(client, "element_sets", id, elementSet);
    }

    @Override
    protected ElementSet instantiate(JSONObject jo) throws Throwable {
        return new ElementSet(jo);
    }

}
