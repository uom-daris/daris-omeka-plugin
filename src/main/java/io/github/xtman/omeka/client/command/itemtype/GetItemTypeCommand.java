package io.github.xtman.omeka.client.command.itemtype;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntityCommand;
import io.github.xtman.omeka.model.ItemType;

public class GetItemTypeCommand extends GetEntityCommand<ItemType> {

    public GetItemTypeCommand(OmekaClient client, long id) {
        super(client, "item_types", id);
    }

    @Override
    protected ItemType instantiate(JSONObject jo) throws Throwable {
        return new ItemType(jo);
    }

}
