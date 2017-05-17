package io.github.xtman.omeka.client.command.element;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PutEntityCommand;
import io.github.xtman.omeka.model.Element;
import io.github.xtman.omeka.model.builder.ElementBuilder;

public class PutElementCommand extends PutEntityCommand<Element> {

    public PutElementCommand(OmekaClient client, long id, ElementBuilder element) {
        super(client, "elements", id, element);
    }

    @Override
    protected Element instantiate(JSONObject jo) throws Throwable {
        return new Element(jo);
    }

}
