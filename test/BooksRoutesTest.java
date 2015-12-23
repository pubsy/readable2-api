import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;
import play.test.WithServer;

import static org.junit.Assert.assertEquals;

public class BooksRoutesTest extends WithServer {

    @Test
    public void testBooksListOffset() {
        int actualOffset = Json.parse(getResponseBody("/?offset=2&limit=3")).get("properties").get("offset").intValue();

        assertEquals(2, actualOffset);
    }

    @Test
    public void testBooksListLimit() {
        int actualLimit = Json.parse(getResponseBody("/?offset=0&limit=5")).get("properties").get("limit").intValue();

        assertEquals(5, actualLimit);
    }

    @Test
    public void testBooksListDefaultParamValues() {
        String responseBody = getResponseBody("/");
        int actualOffset = Json.parse(responseBody).get("properties").get("offset").intValue();
        int actualLimit = Json.parse(responseBody).get("properties").get("limit").intValue();

        assertEquals(0, actualOffset);
        assertEquals(9, actualLimit);
    }

    private String getResponseBody(String url) {
        return WS.url("http://localhost:" + this.testServer.port() + url).get().get(5000).getBody();
    }
}
