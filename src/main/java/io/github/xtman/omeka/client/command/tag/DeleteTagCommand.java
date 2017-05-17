package io.github.xtman.omeka.client.command.tag;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.DeleteEntityCommand;

public class DeleteTagCommand extends DeleteEntityCommand {

    public DeleteTagCommand(OmekaClient client, long id) {
        super(client, "tags", id);
    }

}
