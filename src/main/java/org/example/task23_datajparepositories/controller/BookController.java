package org.example.task23_datajparepositories.controller;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.example.task23_datajparepositories.entity.Author;
import org.example.task23_datajparepositories.entity.Book;
import org.example.task23_datajparepositories.repository.AuthorRepository;
import org.example.task23_datajparepositories.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private Faker faker;

    @PostConstruct
    public void generationData() {
        if(bookRepository.count() == 0) {
            List<Author> authors = authorRepository.findAll();
            for (int i = 0; i < 100; i++) {
                Book book = new Book();
                book.setTitle(faker.book().title());
                book.setIsbn(faker.regexify("978-[0-9]-[0-9]{5}-[0-9]{3}-[0-9]"));
                book.setPublishedYear(faker.number().numberBetween(1900, 2024));
                book.setPrice(faker.number().randomDouble(2, 100, 2000));
                book.setPages(faker.number().numberBetween(10, 1000));
                Author author = authors.get(faker.number().numberBetween(0, authors.size()));
                book.setAuthor(author);
                bookRepository.save(book);
            }
        }
    }

    @GetMapping("/getByTitle")
    public ResponseEntity<List<Book>> getByTitle(
            @RequestParam(name = "title") String title
    ) {
        List<Book> bookList = bookRepository.findByTitle(title);
        if(bookList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
         return ResponseEntity.ok(bookList);
    }

    @GetMapping("/getByPriceGreaterThan")
    public ResponseEntity<List<Book>> getByPriceGreaterThan(
            @RequestParam(name = "price") Double price
    ) {
        List<Book> bookList = bookRepository.findByPriceGreaterThan(price);
        if(bookList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookList);
    }

    @GetMapping("/getByPublishedYearBetween")
    public ResponseEntity<List<Book>> getByPublishedYearBetween(
            @RequestParam(name = "startYear") Integer startYear,
            @RequestParam(name = "endYear") Integer endYear
    ) {
        List<Book> bookList = bookRepository.findByPublishedYearBetween(startYear, endYear);
        if(bookList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookList);
    }

    @GetMapping("/getByAuthor_Id")
    public ResponseEntity<List<Book>> getByAuthor_Id(
            @RequestParam(name = "author_id") Long author_id
    ) {
        List<Book> bookList = bookRepository.findByAuthor_Id(author_id);
        if(bookList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookList);
    }

    @GetMapping("/getByPagesLessThan")
    public ResponseEntity<List<Book>> getByPagesLessThan(
            @RequestParam(name = "pages") Integer pages
    ) {
        List<Book> bookList = bookRepository.findByPagesLessThan(pages);
        if(bookList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookList);
    }

    @GetMapping("/getByTitleContaining")
    public ResponseEntity<List<Book>> getByTitleContaining(
            @RequestParam(name = "word") String word
    ) {
        List<Book> bookList = bookRepository.findByTitleContaining(word);
        if(bookList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookList);
    }



}
