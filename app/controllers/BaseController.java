package controllers;

import actions.BasicAuth;
import com.google.code.siren4j.component.Action;
import com.google.code.siren4j.component.Entity;
import com.google.code.siren4j.component.Link;
import com.google.code.siren4j.component.builder.ActionBuilder;
import com.google.code.siren4j.component.builder.LinkBuilder;
import com.google.code.siren4j.converter.ReflectingConverter;
import com.google.code.siren4j.converter.ResourceConverter;
import com.google.code.siren4j.error.Siren4JException;
import com.google.code.siren4j.resource.Resource;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by acidum on 12/14/15.
 */
public class BaseController extends Controller {

    @BasicAuth
    public Result login() {
        return ok("Successfully authenticated");
    }

    protected String serializeResource(Resource resource) {
        addGenericControls(resource);

        ResourceConverter converter = null;
        try {
            converter = ReflectingConverter.newInstance();
        } catch (Siren4JException e) {
            e.printStackTrace();
        }
        Entity entity = converter.toEntity(resource);
        return entity.toString();
    }


    protected void addGenericControls(Resource resource) {
        Collection<Link> links = new ArrayList<Link>();
        links.add(LinkBuilder.newInstance()
                .setHref("/users")
                .setRelationship("users")
                .setTitle("Users")
                .build());
        links.add(LinkBuilder.newInstance()
                .setHref("/books-search")
                .setRelationship("search")
                .setTitle("Search")
                .build());
        resource.setEntityLinks(links);

        Collection<Action> actions = new ArrayList<Action>();
        actions.add(ActionBuilder.newInstance()
        .setHref("/register")
        .setName("register")
        .setTitle("Register")
        .build());
        resource.setEntityActions(actions);
    }

}
