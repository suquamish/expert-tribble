package tld.kata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FnBooks {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void booksReturnsAllBooks() throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity("/books", String.class);

        assertEquals( HttpStatus.OK, response.getStatusCode());
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray booksArray = jsonResponse.getJSONArray("books");
        assertEquals(50, booksArray.length());
    }

    @Test
    void booksCanBeFilteredByTitleKeyword() throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity("/books/title?keyword=Silver", String.class);

        assertEquals( HttpStatus.OK, response.getStatusCode());
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray booksArray = jsonResponse.getJSONArray("books");
        assertEquals(2, booksArray.length());
    }

    @Test
    void booksCanBeFilteredByAuthorLastName() throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity("/books/author?last_name=Blackwood", String.class);

        assertEquals( HttpStatus.OK, response.getStatusCode());
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray booksArray = jsonResponse.getJSONArray("books");
        assertEquals(3, booksArray.length());
    }

    @Test
    void booksCanBeFilteredByAuthorFirstName() throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity("/books/author?first_name=Jasper", String.class);

        assertEquals( HttpStatus.OK, response.getStatusCode());
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray booksArray = jsonResponse.getJSONArray("books");
        assertEquals(2, booksArray.length());
    }

    @Test
    void booksCanBeFilteredByAuthorMiddleName() throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity("/books/author?middle_name=May", String.class);

        assertEquals( HttpStatus.OK, response.getStatusCode());
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray booksArray = jsonResponse.getJSONArray("books");
        assertEquals(2, booksArray.length());
    }

    @Test
    void booksFilteredByAuthorValidate() {
        ResponseEntity<String> response = restTemplate.getForEntity("/books/author?first_name=&middle_name=&last_name=", String.class);

        assertEquals( HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
