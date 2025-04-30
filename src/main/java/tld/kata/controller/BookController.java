package tld.kata.controller;

import tld.kata.domain.BooksResponse;
import tld.kata.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(method = RequestMethod.GET)
    public BooksResponse getAllBooks() {
        BooksResponse response = new BooksResponse();
        response.setBooks(bookService.allBooks());
        return response;
    }

    @GetMapping("/title")
    public BooksResponse getBookByTitleKeyword(@RequestParam String keyword) {
        BooksResponse response = new BooksResponse();
        response.setBooks(bookService.booksByTitle(keyword));
        return response;
    }

    @GetMapping("/author")
    public ResponseEntity<BooksResponse> getBookByAuthor(
            @RequestParam(value = "last_name", required = false) String lastName,
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "middle_name", required = false) String middleInitial
    ) {
        BooksResponse response = new BooksResponse();
        ResponseEntity<BooksResponse> entity;
        if(atLeastOneNamePresent(firstName, middleInitial, lastName)) {
            response.setBooks(bookService.booksByAuthor(firstName, middleInitial, lastName));
            entity = ResponseEntity.ok(response);
        } else {
            entity = ResponseEntity.badRequest().build();
        }
        return entity;
    }

    private boolean atLeastOneNamePresent(String... names) {
        for(String name : names) {
            if(name != null && !name.isEmpty()) return true;
        }
        return false;
    }
}
