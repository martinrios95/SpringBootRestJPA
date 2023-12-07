package com.springboot.restJPA.controller;

import com.springboot.rest.response.ServiceResponse;
import com.springboot.restJPA.models.Book;
import com.springboot.restJPA.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private final BookRepository repository;

    public BookController(BookRepository repository){
        this.repository = repository;
    }

    @GetMapping("/books")
    public ResponseEntity<Iterable<Book>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ServiceResponse<Book>> get(@PathVariable("id") String id){
        Optional<Book> boxBook = repository.findById(id);

        ServiceResponse<Book> serviceResponse = new ServiceResponse<>();

        if (boxBook.isEmpty()){
            serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
            serviceResponse.setMessage("Book not found!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        }

        serviceResponse.setCode(HttpStatus.OK.value());
        serviceResponse.setData(boxBook.get());

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<ServiceResponse> add(@RequestBody Book book){
        Book currentBook = repository.save(book);

        ServiceResponse<Book> serviceResponse = new ServiceResponse<>();

        if (currentBook == null){
            serviceResponse.setCode(HttpStatus.BAD_REQUEST.value());
            serviceResponse.setMessage("Couldn't add book!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
        }

        serviceResponse.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<ServiceResponse<Book>> update(@PathVariable("id") String id, @RequestBody Book book){
        Optional<Book> boxBook = repository.findById(id);

        ServiceResponse<Book> serviceResponse = new ServiceResponse<>();

        if (boxBook.isEmpty()){
            serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
            serviceResponse.setMessage("Book not found!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        }

        book.setId(id); // Set ID to avoid new insert
        Book currentBook = repository.save(book);

        if (currentBook == null){
            serviceResponse.setCode(HttpStatus.BAD_REQUEST.value());
            serviceResponse.setMessage("Couldn't update book!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
        }

        serviceResponse.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<ServiceResponse<Book>> delete(@PathVariable String id){
        Optional<Book> boxBook = repository.findById(id);

        ServiceResponse<Book> serviceResponse = new ServiceResponse<>();

        if (boxBook.isEmpty()){
            serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
            serviceResponse.setMessage("Book not found!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        }

        repository.deleteById(id);

        serviceResponse.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
