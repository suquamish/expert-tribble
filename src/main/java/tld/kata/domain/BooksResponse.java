package tld.kata.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

public class BooksResponse {
    @Getter
    @Setter
    private List<Book> books = Collections.emptyList();
}
