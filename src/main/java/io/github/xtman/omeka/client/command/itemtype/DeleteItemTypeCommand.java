package io.github.xtman.omeka.client.command.itemtype;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.DeleteEntityCommand;

public class DeleteItemTypeCommand extends DeleteEntityCommand {

    public DeleteItemTypeCommand(OmekaClient client, long id) {
        super(client, "item_types", id);
    }

}
