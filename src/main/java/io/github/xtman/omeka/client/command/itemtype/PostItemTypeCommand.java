package io.github.xtman.omeka.client.command.itemtype;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PostEntityCommand;
import io.github.xtman.omeka.model.ItemType;
import io.github.xtman.omeka.model.builder.ItemTypeBuilder;

public class PostItemTypeCommand extends PostEntityCommand<ItemType> {

    public PostItemTypeCommand(OmekaClient client, ItemTypeBuilder itemType) {
        super(client, "item_types", itemType);
    }

    @Override
    protected ItemType instantiate(JSONObject jo) throws Throwable {
        return new ItemType(jo);
    }

}
