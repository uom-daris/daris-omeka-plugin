package io.github.xtman.omeka.client.command.item;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.DeleteEntityCommand;

public class DeleteItemCommand extends DeleteEntityCommand {

    public DeleteItemCommand(OmekaClient client, long id) {
        super(client, "items", id);
    }

}
