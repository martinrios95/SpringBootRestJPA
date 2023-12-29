package com.springboot.restJPA.controller;

import com.springboot.restJPA.models.Book;
import com.springboot.restJPA.repository.BookRepository;
import com.springboot.restJPA.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private final BookRepository repository;

    public BookController(BookRepository repository){
        this.repository = repository;
    }

    @GetMapping()
    public ResponseEntity<Iterable<Book>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id){
        Optional<Book> boxBook = repository.findById(id);

        ServiceResponse<Book> serviceResponse = new ServiceResponse<>();

        if (boxBook.isEmpty()){
            serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
            serviceResponse.setMessage("Book not found!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(boxBook);
    }

    @PostMapping()
    public ResponseEntity<ServiceResponse> add(@RequestBody Book book){
        Book currentBook = repository.save(book);

        ServiceResponse serviceResponse = new ServiceResponse<>();

        if (currentBook == null){
            serviceResponse.setCode(HttpStatus.BAD_REQUEST.value());
            serviceResponse.setMessage("Couldn't add book!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
        }

        serviceResponse.setCode(HttpStatus.OK.value());

        return ResponseEntity.ok(serviceResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable("id") String id, @RequestBody Book book){
        Optional<Book> boxBook = repository.findById(id);

        ServiceResponse serviceResponse = new ServiceResponse<>();

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

        return ResponseEntity.ok(serviceResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> delete(@PathVariable String id){
        Optional<Book> boxBook = repository.findById(id);

        ServiceResponse serviceResponse = new ServiceResponse<>();

        if (boxBook.isEmpty()){
            serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
            serviceResponse.setMessage("Book not found!");

            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        }

        repository.deleteById(id);

        serviceResponse.setCode(HttpStatus.OK.value());

        return ResponseEntity.ok(serviceResponse);
    }
}
