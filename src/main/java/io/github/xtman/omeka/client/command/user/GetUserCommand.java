package io.github.xtman.omeka.client.command.user;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.GetEntityCommand;
import io.github.xtman.omeka.model.User;

public class GetUserCommand extends GetEntityCommand<User> {

    public GetUserCommand(OmekaClient client, long id) {
        super(client, "users", id);
    }

    @Override
    protected User instantiate(JSONObject jo) throws Throwable {
        return new User(jo);
    }

}
