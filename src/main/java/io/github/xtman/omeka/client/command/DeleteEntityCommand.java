package io.github.xtman.omeka.client.command;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.util.PathUtils;

public class DeleteEntityCommand extends DeleteCommand {

    protected DeleteEntityCommand(OmekaClient client, String prefix, long id) {
        super(client, PathUtils.join(prefix, Long.toString(id)));
    }

}
