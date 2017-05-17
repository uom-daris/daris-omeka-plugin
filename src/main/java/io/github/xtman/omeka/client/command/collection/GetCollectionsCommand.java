package io.github.xtman.omeka.client.command.collection;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntitySetCommand;
import io.github.xtman.omeka.client.util.OmekaDateUtils;
import io.github.xtman.omeka.model.Collection;

public class GetCollectionsCommand extends GetEntitySetCommand<Collection> {

    public static class Params extends GetEntitySetCommand.Params<Params> {

        public Params setPublic(Boolean isPublic) {
            put("public", isPublic);
            return this;
        }

        public Params setFeatured(Boolean isFeatured) {
            put("featured", isFeatured);
            return this;
        }

        public Params setAddedSince(Date addedSince) {
            put("added_since", OmekaDateUtils.formatDate(addedSince));
            return this;
        }

        public Params setModifiedSince(Date modifiedSince) {
            put("modified_since", OmekaDateUtils.formatDate(modifiedSince));
            return this;
        }

        public Params setOwner(Long ownerId) {
            put("owner", ownerId);
            return this;
        }

    }

    public GetCollectionsCommand(OmekaClient client, Params params) {
        super(client, "collections", params);
    }

    @Override
    protected List<Collection> instantiate(JSONArray ja) throws Throwable {
        return Collection.instantiateCollections(ja);
    }

}
