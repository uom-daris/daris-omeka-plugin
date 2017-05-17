package io.github.xtman.omeka.client.command.element;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.DeleteEntityCommand;

public class DeleteElementCommand extends DeleteEntityCommand {

    public DeleteElementCommand(OmekaClient client, long id) {
        super(client, "elements", id);
    }

}
