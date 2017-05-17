package io.github.xtman.omeka.client.command.item;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntityCommand;
import io.github.xtman.omeka.model.Item;

public class GetItemCommand extends GetEntityCommand<Item> {

    public GetItemCommand(OmekaClient client, long id) {
        super(client, "items", id);
    }

    @Override
    protected Item instantiate(JSONObject jo) throws Throwable {
        return new Item(jo);
    }

}
