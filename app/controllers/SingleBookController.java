package controllers;

import play.mvc.Result;
import resources.BookResource;
import services.BooksService;

import javax.inject.Inject;

public class SingleBookController extends BaseController {

    private BooksService booksService;

    public Result book(String externalId) {
        BookResource book = getBook(externalId);

        return ok(serializeResource(book)).as("application/vnd.siren+json");
    }

    private BookResource getBook(String externalId) {
        return booksService.getBook(externalId);
    }

    @Inject
    public void setBooksService(BooksService booksService) {
        this.booksService = booksService;
    }
}
