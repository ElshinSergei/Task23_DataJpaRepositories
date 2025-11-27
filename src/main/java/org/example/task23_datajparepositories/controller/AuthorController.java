package org.example.task23_datajparepositories.controller;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.example.task23_datajparepositories.entity.Author;
import org.example.task23_datajparepositories.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private Faker faker;

    @PostConstruct
    public void generationData() {
        if(authorRepository.count() == 0) {
            for (int i = 0; i < 100; i++) {
                Author author = new Author();
                author.setFirstName(faker.name().firstName());
                author.setLastName(faker.name().lastName());
                author.setEmail(faker.internet().emailAddress());
                author.setAge(faker.number().numberBetween(10, 100));
                author.setCountry(faker.country().name());
                author.setCreatedAt(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 3650)));

                authorRepository.save(author);
            }
        }
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<Author> getByEmail(
            @PathVariable(name = "email") String email
    ) {
        Author author = authorRepository.findByEmail(email);
        if(author != null) {
            return ResponseEntity.ok(author);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getByAgeGreaterThan/{age}")
    public ResponseEntity<List<Author>> getByAgeGreaterThan(@PathVariable(name = "age") int age) {

        List<Author> authors = authorRepository.findByAgeGreaterThan(age);
        if(authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/getByAgeBetween")
    public ResponseEntity<List<Author>> getByAgeBetween(
            @RequestParam(name = "start") int start,
            @RequestParam(name = "end") int end
    ) {
        List<Author> authors = authorRepository.findByAgeBetween(start, end);
        if(authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/getByCountry")
    public ResponseEntity<List<Author>> getByCountry(
            @RequestParam(name = "country") String country
    ) {
        List<Author> authors = authorRepository.findByCountry(country);
        if(authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/getByFirstNameOrLastName")
    public ResponseEntity<List<Author>> getByFirstNameOrLastName(
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName
    ) {
        List<Author> authors = authorRepository.findByFirstNameOrLastName(firstName, lastName);
        if(authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/getByCountryOrderByAgeDesc")
    public ResponseEntity<List<Author>> getByCountryOrderByAgeDesc(
            @RequestParam(name = "country") String country
    ) {
        List<Author> authors = authorRepository.findByCountryOrderByAgeDesc(country);
        if(authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/getTop3ByOrderByAgeDesc")
    public ResponseEntity<List<Author>> getTop3ByOrderByAgeDesc() {

        List<Author> authors = authorRepository.findTop3ByOrderByAgeDesc();
        if(authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authors);
    }

}
