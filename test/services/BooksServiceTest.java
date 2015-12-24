package services;

import models.Book;
import org.junit.Test;
import play.test.WithApplication;
import resources.BookResource;

import java.util.List;

import static org.junit.Assert.*;

public class BooksServiceTest extends WithApplication {

    private BooksService booksService = new BooksService();

    @Test
    public void testGetBooksOffset(){
        createBooks(9);

        List<BookResource> booksList = booksService.getBooksList(3, 3);

        assertEquals("3", booksList.get(0).externalId);
        assertEquals("4", booksList.get(1).externalId);
        assertEquals("5", booksList.get(2).externalId);
    }

    @Test
    public void testGetBooksLimit(){
        createBooks(9);

        List<BookResource> booksList = booksService.getBooksList(3, 2);

        assertEquals(2, booksList.size());
    }

    @Test
    public void testGetEmptyBooksList(){
        List<BookResource> booksList = booksService.getBooksList(0, 3);

        assertNotNull(booksList);
        assertEquals(0, booksList.size());
    }

    @Test
    public void testGetBooksTotal(){
        createBooks(5);

        int booksTotal = booksService.getBooksTotal();

        assertEquals(5, booksTotal);
    }

    @Test
    public void testGetNotExistingBook(){
        BookResource book = booksService.getBook("5");

        assertNull(book);
    }

    @Test
    public void testGetBookByExternalId(){
        createBooks(3);

        BookResource book = booksService.getBook("1");

        assertNotNull(book);
        assertEquals("1", book.externalId);
    }


    private void createBooks(int count) {
        for(int i = 0; i < count; i++){
            Book book = new Book();
            book.externalId = Integer.toString(i);
            book.save();
            sleepForOneMillisecond();
        }

    }

    // Sometimes entities are saved so quickly, that they have the same timestamp.
    private void sleepForOneMillisecond() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
