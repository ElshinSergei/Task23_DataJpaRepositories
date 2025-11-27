package org.example.task23_datajparepositories.repository;

import org.example.task23_datajparepositories.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByEmail(String email);
    List<Author> findByAgeGreaterThan(int age);
    List<Author> findByAgeBetween(int start, int end);
    List<Author> findByCountry(String country);
    List<Author> findByFirstNameOrLastName(String firstName, String lastName);
    List<Author> findByCountryOrderByAgeDesc(String country);
    List<Author> findTop3ByOrderByAgeDesc();

}
