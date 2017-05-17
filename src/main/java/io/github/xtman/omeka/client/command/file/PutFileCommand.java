package io.github.xtman.omeka.client.command.file;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PutEntityCommand;
import io.github.xtman.omeka.model.File;
import io.github.xtman.omeka.model.builder.FileBuilder;

public class PutFileCommand extends PutEntityCommand<File> {

    public PutFileCommand(OmekaClient client, long id, FileBuilder element) {
        super(client, "files", id, element);
    }

    @Override
    protected File instantiate(JSONObject jo) throws Throwable {
        return new File(jo);
    }

}
