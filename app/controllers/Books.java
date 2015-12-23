package controllers;

import com.google.code.siren4j.resource.CollectionResource;
import play.mvc.Result;
import resources.BookResource;
import resources.BooksListResource;
import services.BooksService;

import javax.inject.Inject;
import java.util.List;

public class Books extends BaseController {

    @Inject
    private BooksService booksService;

    public Result books() {
        BooksListResource booksListResource = new BooksListResource();
        booksListResource.books = new CollectionResource<BookResource>();
        booksListResource.books.setItems(getBooksList(0, 9));
        booksListResource.books.setLimit(9);
        booksListResource.books.setOffset(0);
        booksListResource.books.setTotal(getBooksTotal());

        addGenericControls(booksListResource);
        return ok(serializeResource(booksListResource)).as("application/vnd.siren+json");
    }

    private List<BookResource> getBooksList(int limit, int offset) {
        return booksService.getBooksList(limit, offset);
    }

    private long getBooksTotal() {
        return booksService.getBooksTotal();
    }

    public void setBooksService(BooksService booksService){
        this.booksService  = booksService;
    }
}
