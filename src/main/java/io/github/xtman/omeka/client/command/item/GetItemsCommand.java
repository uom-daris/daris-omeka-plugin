package io.github.xtman.omeka.client.command.item;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntitySetCommand;
import io.github.xtman.omeka.client.util.OmekaDateUtils;
import io.github.xtman.omeka.model.Item;

public class GetItemsCommand extends GetEntitySetCommand<Item> {

    public static class Params extends GetEntitySetCommand.Params <Params> {

        public Params setCollection(Long collectionId) {
            put("collection", collectionId);
            return this;
        }

        public Params setItemType(Long itemTypeId) {
            put("item_type", itemTypeId);
            return this;
        }

        public Params setFeatured(Boolean featured) {
            put("featured", featured);
            return this;
        }

        public Params setPublic(Boolean isPublic) {
            put("public", isPublic);
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

        public Params setOwner(Long userId) {
            put("owner", userId);
            return this;
        }

    }

    public GetItemsCommand(OmekaClient client, Params params) {
        super(client, "items", params);
    }

    @Override
    protected List<Item> instantiate(JSONArray ja) throws Throwable {
        return Item.instantiateItems(ja);
    }

}
