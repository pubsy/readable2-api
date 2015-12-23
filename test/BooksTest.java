import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import controllers.Books;
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
import services.BooksService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;

@RunWith(MockitoJUnitRunner.class)
public class BooksTest extends WithApplication {

    @Mock
    private BooksService booksService;

    @InjectMocks
    private Books books = new Books();

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Override
    protected FakeApplication provideFakeApplication() {
        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
                ImmutableMap.of("play.http.router", "router.Routes"), new ArrayList<String>(), null);
    }

    @Test
    public void testBooksListContentType() {
        Result result = books.books();
        assertEquals(OK, result.status());
        assertEquals("application/vnd.siren+json", result.contentType());
    }

    @Test
    public void testBooksListContainsLinks() {
        assertLinksPresent(books.books(), "self", "search", "users");
    }

    @Test
    public void testBooksListContainsActions() {
        assertActionsPresent(books.books(), "register");
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

    private void assertValuesPresent(Set<String> actualValues, String... expectedValues){
        Set<String> expectedValuesSet = Arrays.
                stream(expectedValues).
                collect(Collectors.toSet());

        expectedValuesSet.removeAll(actualValues);
        if(!expectedValuesSet.isEmpty()){
            fail("Expected but missing values: " + expectedValuesSet);
        }
    }
}
