package controllers;

import com.google.code.siren4j.resource.CollectionResource;
import play.mvc.Result;
import resources.BookResource;
import services.BooksService;

import javax.inject.Inject;
import java.util.List;

public class BooksController extends BaseController {

    private BooksService booksService;

    public Result books(int offset, int limit) {
        CollectionResource<BookResource> books = new CollectionResource<BookResource>();
        books.setItems(getBooksList(offset, limit));
        books.setLimit(limit);
        books.setOffset(offset);
        books.setTotal(getBooksTotal());

        return ok(serializeResource(books)).as("application/vnd.siren+json");
    }

    private List<BookResource> getBooksList(int offset, int limit) {
        return booksService.getBooksList(offset, limit);
    }

    private long getBooksTotal() {
        return booksService.getBooksTotal();
    }

    @Inject
    public void setBooksService(BooksService booksService){
        this.booksService  = booksService;
    }
}
