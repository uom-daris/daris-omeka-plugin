package io.github.xtman.omeka.client.command.elementset;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.DeleteEntityCommand;

public class DeleteElementSetCommand extends DeleteEntityCommand {

    public DeleteElementSetCommand(OmekaClient client, long id) {
        super(client, "element_sets", id);
    }

}
