package com.ajierro;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

public class TypesControllerTest {

    private static EmbeddedServer server;
    private static HttpClient client;

    @BeforeClass
    public static void setupServer() {
        server = ApplicationContext
                .build()
                .run(EmbeddedServer.class);
        client = server.getApplicationContext().createBean(HttpClient.class, server.getURL());
    }

    @AfterClass
    public static void stopServer() {
        if (server != null) {
            server.stop();
        }
        if (client != null) {
            client.stop();
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void supplyAnInvalidOrderTriggersValidationFailure() {
        thrown.expect(HttpClientResponseException.class);
        thrown.expect(hasProperty("response", hasProperty("status", is(HttpStatus.BAD_REQUEST))));
        client.toBlocking().exchange(HttpRequest.GET("/api/v1/types?order=foo"));
    }

    @Test
    public void testFindNonExistingTypesReturns404() {
        thrown.expect(HttpClientResponseException.class);
        thrown.expect(hasProperty("response", hasProperty("status", is(HttpStatus.NOT_FOUND))));
        client.toBlocking().exchange(HttpRequest.GET("/api/v1/types/10000"));
    }

    /*@Test
    public void testTypesCrudOperations() {

        List<Long> typesIds = new ArrayList<>();

        HttpRequest request = HttpRequest.POST("/api/v1/types", new TypesSaveCommand("Acid", "The best for hallucinate"));
        HttpResponse response = client.toBlocking().exchange(request);
        typesIds.add(entityId(response));

        assertEquals(HttpStatus.CREATED, response.getStatus());

        request = HttpRequest.POST("/api/v1/types", new TypesSaveCommand("Proteins", "To grow all muscles"));
        response = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.CREATED, response.getStatus());

        Long id = entityId(response);
        typesIds.add(id);
        request = HttpRequest.GET("/api/v1/types/"+id);

        Types type = client.toBlocking().retrieve(request, Types.class);

        assertEquals("Acid", type.getName());

        request = HttpRequest.PUT("/api/v1/types", new TypesUpdateCommand("Detox2", null));
        response = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());

        request = HttpRequest.GET("/api/v1/types/" + id);
        type = client.toBlocking().retrieve(request, Types.class);
        assertEquals("Micro-services", type.getName());

        request = HttpRequest.GET("/api/v1/types");
        List<Types> types = client.toBlocking().retrieve(request, Argument.of(List.class, Types.class));

        assertEquals(2, types.size());

        request = HttpRequest.GET("/api/v1/types?max=1");
        types = client.toBlocking().retrieve(request, Argument.of(List.class, Types.class));

        assertEquals(1, types.size());
        assertEquals("DevOps", types.get(0).getName());

        request = HttpRequest.GET("/api/v1/types?max=1&order=desc&sort=name");
        types = client.toBlocking().retrieve(request, Argument.of(List.class, Types.class));

        assertEquals(1, types.size());
        assertEquals("Micro-services", types.get(0).getName());

        request = HttpRequest.GET("/api/v1/types?max=1&offset=10");
        types = client.toBlocking().retrieve(request, Argument.of(List.class, Types.class));

        assertEquals(0, types.size());

        for (Long typesId : typesIds) {
            request = HttpRequest.DELETE("/genres/"+typesId);
            response = client.toBlocking().exchange(request);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
        }
    }

    protected Long entityId(HttpResponse response) {
        String path = "/genres/";
        String value = response.header(HttpHeaders.LOCATION);
        if ( value == null) {
            return null;
        }
        int index = value.indexOf(path);
        if ( index != -1) {
            return Long.valueOf(value.substring(index + path.length()));
        }
        return null;
    }*/
}
