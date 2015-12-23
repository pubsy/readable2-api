package services;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import models.Book;
import resources.BookResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acidum on 12/14/15.
 */
public class BooksService {

    private Function<Book, BookResource> booksToResources =
            new Function<Book, BookResource>() {
                public BookResource apply(Book book) {
                    return new BookResource(book);
                }
            };

    public List<BookResource> getBooksList(int offset, int limit) {
        //Book.find("order by insertedAt asc").fetch(page + 1, size);

        List<Book> books = new ArrayList<Book>();
        Book book = new Book();
        book.title = "bla";
        book.description = "sososo";
        book.externalId = "ext123";
        books.add(book);

        return Lists.transform(books, booksToResources);//Book.find.all();
    }

    public int getBooksTotal() {
        return Book.find.findRowCount();
    }
}
