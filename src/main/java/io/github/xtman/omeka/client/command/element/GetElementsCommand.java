package io.github.xtman.omeka.client.command.element;

import java.util.List;

import org.json.JSONArray;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntitySetCommand;
import io.github.xtman.omeka.model.Element;

public class GetElementsCommand extends GetEntitySetCommand<Element> {

    public static class Params extends GetEntitySetCommand.Params<Params> {

        public Params setElementSet(Long elementSetId) {
            put("element_set", elementSetId);
            return this;
        }

        public Params setName(String name) {
            put("name", name);
            return this;
        }

        public Params setItemType(Long itemType) {
            put("item_type", itemType);
            return this;
        }

    }

    public GetElementsCommand(OmekaClient client, Params params) {
        super(client, "elements", params);
    }

    @Override
    protected List<Element> instantiate(JSONArray ja) throws Throwable {
        return Element.instantiateElements(ja);
    }

}
