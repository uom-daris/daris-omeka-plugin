package io.github.xtman.omeka.client.command.collection;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.DeleteEntityCommand;

public class DeleteCollectionCommand extends DeleteEntityCommand {

    public DeleteCollectionCommand(OmekaClient client, long id) {
        super(client, "collections", id);
    }

}
