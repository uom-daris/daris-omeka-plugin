package io.github.xtman.omeka.client.command.tag;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntityCommand;
import io.github.xtman.omeka.model.Tag;

public class GetTagCommand extends GetEntityCommand<Tag> {

    public GetTagCommand(OmekaClient client, long id) {
        super(client, "tags", id);
    }

    @Override
    protected Tag instantiate(JSONObject jo) throws Throwable {
        return new Tag(jo);
    }

}
