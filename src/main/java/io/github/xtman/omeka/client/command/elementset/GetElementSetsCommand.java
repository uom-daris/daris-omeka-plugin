package io.github.xtman.omeka.client.command.elementset;

import java.util.List;

import org.json.JSONArray;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntitySetCommand;
import io.github.xtman.omeka.model.ElementSet;

public class GetElementSetsCommand extends GetEntitySetCommand<ElementSet> {

    public static class Params extends GetEntitySetCommand.Params<Params> {
        public Params setName(String name) {
            put("name", name);
            return this;
        }

        public Params setRecordType(String recordType) {
            put("record_type", recordType);
            return this;
        }
    }

    public GetElementSetsCommand(OmekaClient client, Params params) {
        super(client, "element_sets", params);
    }

    @Override
    protected List<ElementSet> instantiate(JSONArray ja) throws Throwable {
        return ElementSet.instantiateElementSets(ja);
    }

}
