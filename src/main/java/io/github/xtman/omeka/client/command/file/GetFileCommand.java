package io.github.xtman.omeka.client.command.file;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntityCommand;
import io.github.xtman.omeka.model.File;

public class GetFileCommand extends GetEntityCommand<File> {

    public GetFileCommand(OmekaClient client, long id) {
        super(client, "files", id);
    }

    @Override
    protected File instantiate(JSONObject jo) throws Throwable {
        return new File(jo);
    }

}
