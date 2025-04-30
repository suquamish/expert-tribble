package tld.kata.service;

import tld.kata.domain.Book;
import tld.kata.domain.BookCollection;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final List<Book> allBooks = BookCollection.BOOKS;

    public List<Book> allBooks() {
        return allBooks;
    }

    public List<Book> booksByTitle(String keyword) {
        return allBooks
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> booksByAuthor(String firstName, String middleInitial, String lastName) {
        return allBooks
                .stream()
                .filter( book -> authorNameMatches(book.getAuthor(), firstName, middleInitial, lastName))
                .collect(Collectors.toList());
    }

    public boolean authorNameMatches(String author, String firstName, String middle, String lastName) {
        String[] authorNames = author.split(" ");
        boolean firstNameMatches = false;
        boolean middleMatches = false;
        boolean lastNameMatches = false;

        if(notNullOrEmpty(firstName))
            firstNameMatches = firstName.equalsIgnoreCase(authorNames[0]);
        if(notNullOrEmpty(lastName))
            lastNameMatches = lastName.equalsIgnoreCase(authorNames[authorNames.length - 1]);
        if(notNullOrEmpty(middle) && authorNames.length > 2)
            middleMatches = middle.equalsIgnoreCase(authorNames[1]);

        return firstNameMatches || lastNameMatches || middleMatches;
    }

    private boolean notNullOrEmpty(String value) {
        return value != null && !value.isBlank();
    }
}
