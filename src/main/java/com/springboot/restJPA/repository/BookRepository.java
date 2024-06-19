package com.springboot.restJPA.repository;

import com.springboot.restJPA.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    public List<Book> findByNameIgnoreCaseContaining(String name);
}
