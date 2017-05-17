package io.github.xtman.omeka.client.command.itemtype;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PutEntityCommand;
import io.github.xtman.omeka.model.ItemType;
import io.github.xtman.omeka.model.builder.ItemTypeBuilder;

public class PutItemTypeCommand extends PutEntityCommand<ItemType> {

    public PutItemTypeCommand(OmekaClient client, long id, ItemTypeBuilder itemType) {
        super(client, "item_types", id, itemType);
    }

    @Override
    protected ItemType instantiate(JSONObject jo) throws Throwable {
        return new ItemType(jo);
    }

}
