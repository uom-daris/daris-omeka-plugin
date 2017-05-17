package io.github.xtman.omeka.client.command.collection;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PostEntityCommand;
import io.github.xtman.omeka.model.Collection;
import io.github.xtman.omeka.model.builder.CollectionBuilder;

public class PostCollectionCommand extends PostEntityCommand<Collection> {

    public PostCollectionCommand(OmekaClient client, CollectionBuilder collection) {
        super(client, "collections", collection);
    }

    @Override
    protected Collection instantiate(JSONObject jo) throws Throwable {
        return new Collection(jo);
    }

}
