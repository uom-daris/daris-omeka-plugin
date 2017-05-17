package io.github.xtman.omeka.client.command.tag;

import java.util.List;

import org.json.JSONArray;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntitySetCommand;
import io.github.xtman.omeka.model.Tag;

public class GetTagsCommand extends GetEntitySetCommand<Tag> {

    public static class Params extends GetEntitySetCommand.Params<Params> {

    }

    public GetTagsCommand(OmekaClient client, Params params) {
        super(client, "tags", params);
    }

    @Override
    protected List<Tag> instantiate(JSONArray ja) throws Throwable {
        return Tag.instantiateTags(ja);
    }

}
