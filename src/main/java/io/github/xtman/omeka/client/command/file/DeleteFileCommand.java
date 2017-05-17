package io.github.xtman.omeka.client.command.file;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.DeleteEntityCommand;

public class DeleteFileCommand extends DeleteEntityCommand {

    public DeleteFileCommand(OmekaClient client, long id) {
        super(client, "files", id);
    }

}
