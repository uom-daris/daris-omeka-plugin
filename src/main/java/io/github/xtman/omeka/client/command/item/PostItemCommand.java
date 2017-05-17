package io.github.xtman.omeka.client.command.item;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PostEntityCommand;
import io.github.xtman.omeka.model.Item;
import io.github.xtman.omeka.model.builder.ItemBuilder;

public class PostItemCommand extends PostEntityCommand<Item> {

    public PostItemCommand(OmekaClient client, ItemBuilder item) {
        super(client, "items", item);
    }

    @Override
    protected Item instantiate(JSONObject jo) throws Throwable {
        return new Item(jo);
    }

}
