import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import controllers.Books;
import models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;
import resources.BookResource;
import services.BooksService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;

@RunWith(MockitoJUnitRunner.class)
public class BooksControllerTest extends WithApplication {

    @Mock
    private BooksService booksService;

    @InjectMocks
    private Books books = new Books();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Override
    protected FakeApplication provideFakeApplication() {
        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
                ImmutableMap.of("play.http.router", "router.Routes"), new ArrayList<String>(), null);
    }

    @Test
    public void testBooksListContentType() {
        Result result = books.books(0, 3);
        assertEquals(OK, result.status());
        assertEquals("application/vnd.siren+json", result.contentType());
    }

    @Test
    public void testBooksListContainsLinks() {
        assertLinksPresent(books.books(0, 3), "search", "users");
    }

    @Test
    public void testBooksListContainsActions() {
        assertActionsPresent(books.books(0, 3), "register");
    }

    @Test
    public void testBooksListSize() {
        prepareBooksService(0, 3, 7);

        int actualSize = Json.parse(contentAsString(books.books(0, 3))).get("properties").get("size").intValue();

        assertEquals(3, actualSize);
    }

    @Test
    public void testBooksListTotal() {
        prepareBooksService(0, 3, 7);

        long actualTotal = Json.parse(contentAsString(books.books(0, 3))).get("properties").get("total").longValue();

        assertEquals(7, actualTotal);
    }

    @Test
    public void testBooksListOffset() {
        prepareBooksService(2, 3, 7);

        int actualTotal = Json.parse(contentAsString(books.books(2, 3))).get("properties").get("offset").intValue();

        assertEquals(2, actualTotal);
    }

    @Test
    public void testBooksListLimit() {
        prepareBooksService(0, 5, 7);

        int actualTotal = Json.parse(contentAsString(books.books(0, 5))).get("properties").get("limit").intValue();

        assertEquals(5, actualTotal);
    }

    private void prepareBooksService(int offset, int limit, int total) {
        ArrayList<BookResource> bookResources = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            bookResources.add(new BookResource(new Book()));
        }
        when(booksService.getBooksList(0, limit)).thenReturn(bookResources);
        when(booksService.getBooksTotal()).thenReturn(total);
    }

    private void assertLinksPresent(Result result, String... expectedLinks) {
        JsonNode links = Json.parse(contentAsString(result)).get("links");
        Set<String> actualLinks = StreamSupport.
                stream(links.spliterator(), false).
                map(e -> e.get("rel").get(0).asText()).
                collect(Collectors.toSet());

        assertValuesPresent(actualLinks, expectedLinks);
    }

    private void assertActionsPresent(Result result, String... expectedActions) {
        JsonNode actions = Json.parse(contentAsString(result)).get("actions");
        Set<String> actualActions = StreamSupport.
                stream(actions.spliterator(), false).
                map(e -> e.get("name").asText()).
                collect(Collectors.toSet());

        assertValuesPresent(actualActions, expectedActions);
    }

    private void assertValuesPresent(Set<String> actualValues, String... expectedValues) {
        Set<String> expectedValuesSet = Arrays.
                stream(expectedValues).
                collect(Collectors.toSet());

        expectedValuesSet.removeAll(actualValues);
        if (!expectedValuesSet.isEmpty()) {
            fail("Expected but missing values: " + expectedValuesSet);
        }
    }
}
