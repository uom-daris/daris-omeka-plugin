package io.github.xtman.omeka.client.command.collection;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntityCommand;
import io.github.xtman.omeka.model.Collection;

public class GetCollectionCommand extends GetEntityCommand<Collection> {

    public GetCollectionCommand(OmekaClient client, long id) {
        super(client, "collections", id);
    }

    @Override
    protected Collection instantiate(JSONObject jo) throws Throwable {
        return new Collection(jo);
    }

}
