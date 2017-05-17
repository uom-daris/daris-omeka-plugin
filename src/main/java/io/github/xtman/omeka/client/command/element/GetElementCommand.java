package io.github.xtman.omeka.client.command.element;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntityCommand;
import io.github.xtman.omeka.model.Element;

public class GetElementCommand extends GetEntityCommand<Element> {

    public GetElementCommand(OmekaClient client, long id) {
        super(client, "elements", id);
    }

    @Override
    protected Element instantiate(JSONObject jo) throws Throwable {
        return new Element(jo);
    }

}
