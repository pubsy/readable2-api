package services;

import com.avaje.ebean.Model;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import models.Book;
import resources.BookResource;

import java.util.ArrayList;
import java.util.List;

public class BooksService {

    public static Model.Finder<String,Book> find = new Model.Finder<String,Book>(
            Book.class
    );

    private Function<Book, BookResource> booksToResources =
            new Function<Book, BookResource>() {
                public BookResource apply(Book book) {
                    return new BookResource(book);
                }
            };

    public List<BookResource> getBooksList(int offset, int limit) {
        List<Book> books = find
                .setFirstRow(offset)
                .setMaxRows(limit)
                .orderBy("insertedAt asc")
                .findList();

        return Lists.transform(books, booksToResources);
    }

    public BookResource getBook(String externalId) {
        Book book = find.where().eq("externalId", externalId).findUnique();
        if(book == null){
            return null;
        }
        return new BookResource(book);
    }

    public int getBooksTotal() {
        return find.findRowCount();
    }
}
