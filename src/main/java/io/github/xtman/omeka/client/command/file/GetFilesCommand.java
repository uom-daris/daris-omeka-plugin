package io.github.xtman.omeka.client.command.file;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntitySetCommand;
import io.github.xtman.omeka.client.util.OmekaDateUtils;
import io.github.xtman.omeka.model.File;

public class GetFilesCommand extends GetEntitySetCommand<File> {

    public static class Params extends GetEntitySetCommand.Params<Params> {

        public Params setItem(Long itemId) {
            put("item", itemId);
            return this;
        }

        public Params setOrder(Long order) {
            put("order", order);
            return this;
        }

        public Params setSizeGreaterThan(Long sizeGreaterThan) {
            put("size_greater_than", sizeGreaterThan);
            return this;
        }

        public Params setMimeType(String mimeType) {
            put("mime_type", mimeType);
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

        public Params setHasDerivativeImage(Boolean hasDerivativeImage) {
            put("has_derivative_image", hasDerivativeImage);
            return this;
        }

    }

    public GetFilesCommand(OmekaClient client, Params params) {
        super(client, "files", params);
    }

    @Override
    protected List<File> instantiate(JSONArray ja) throws Throwable {
        return File.instantiateFiles(ja);
    }

}
