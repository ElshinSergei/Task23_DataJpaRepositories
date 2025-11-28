package org.example.task23_datajparepositories.repository;

import org.example.task23_datajparepositories.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String title);
    List<Book> findByPriceGreaterThan(Double price);
    List<Book> findByPublishedYearBetween(Integer startYear, Integer endYear);
    List<Book> findByAuthor_Id(Long author_Id);
    List<Book> findByPagesLessThan(Integer pages);
    List<Book> findByTitleContaining(String word);

}
