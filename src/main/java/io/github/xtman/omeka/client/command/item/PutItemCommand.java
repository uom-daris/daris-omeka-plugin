package io.github.xtman.omeka.client.command.item;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PutEntityCommand;
import io.github.xtman.omeka.model.Item;
import io.github.xtman.omeka.model.builder.ItemBuilder;

public class PutItemCommand extends PutEntityCommand<Item> {

    public PutItemCommand(OmekaClient client, long id, ItemBuilder item) {
        super(client, "items", id, item);
    }

    @Override
    protected Item instantiate(JSONObject jo) throws Throwable {
        return new Item(jo);
    }

}
