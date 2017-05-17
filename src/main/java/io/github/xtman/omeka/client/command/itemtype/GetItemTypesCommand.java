package io.github.xtman.omeka.client.command.itemtype;

import java.util.List;

import org.json.JSONArray;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntitySetCommand;
import io.github.xtman.omeka.model.ItemType;

public class GetItemTypesCommand extends GetEntitySetCommand<ItemType> {

    public static class Params extends GetEntitySetCommand.Params<Params> {

        public Params setName(String name) {
            put("name", name);
            return this;
        }
    }

    public GetItemTypesCommand(OmekaClient client, Params params) {
        super(client, "item_types", params);
    }

    @Override
    protected List<ItemType> instantiate(JSONArray ja) throws Throwable {
        return ItemType.instantiateItemTypes(ja);
    }

}
