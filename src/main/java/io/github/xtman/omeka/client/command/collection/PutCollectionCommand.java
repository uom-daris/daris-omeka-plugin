package io.github.xtman.omeka.client.command.collection;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PutEntityCommand;
import io.github.xtman.omeka.model.Collection;
import io.github.xtman.omeka.model.builder.EntityBuilder;

public class PutCollectionCommand extends PutEntityCommand<Collection> {

    public PutCollectionCommand(OmekaClient client, long id, EntityBuilder entity) {
        super(client, "collections", id, entity);
    }

    @Override
    protected Collection instantiate(JSONObject jo) throws Throwable {
        return new Collection(jo);
    }

}
