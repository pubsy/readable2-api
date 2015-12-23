package resources;

import com.google.code.siren4j.annotations.Siren4JAction;
import com.google.code.siren4j.annotations.Siren4JEntity;
import com.google.code.siren4j.annotations.Siren4JLink;
import com.google.code.siren4j.annotations.Siren4JSubEntity;

import java.util.ArrayList;
import java.util.List;

import com.google.code.siren4j.resource.BaseResource;
import com.google.code.siren4j.resource.CollectionResource;
import models.User;
//import controllers.SecurityController;

@Siren4JEntity(name = "root", uri = "/",
		links = {
				@Siren4JLink(rel = "search", href = "/books-search", title = "Search")
		},
		actions = {
				@Siren4JAction(href = "/register", name = "register")
		}
)
public class BooksListResource extends BaseResource{

	@Siren4JSubEntity(uri = "/books")
	public CollectionResource<BookResource> books;

}
