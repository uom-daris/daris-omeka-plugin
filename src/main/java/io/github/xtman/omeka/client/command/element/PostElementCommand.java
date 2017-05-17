package io.github.xtman.omeka.client.command.element;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PostEntityCommand;
import io.github.xtman.omeka.model.Element;
import io.github.xtman.omeka.model.builder.ElementBuilder;

public class PostElementCommand extends PostEntityCommand<Element> {

    public PostElementCommand(OmekaClient client, ElementBuilder element) {
        super(client, "elements", element);
    }

    @Override
    protected Element instantiate(JSONObject jo) throws Throwable {
        return new Element(jo);
    }

}
