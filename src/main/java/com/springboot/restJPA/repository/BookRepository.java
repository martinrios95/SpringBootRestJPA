package com.springboot.restJPA.repository;

import com.springboot.restJPA.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    Iterable<Book> findAll();
    Optional<Book> findById(String id);
    Book save(Book book);
    void deleteById(String id);
}
