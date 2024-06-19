package com.springboot.restJPA.controller;

import com.springboot.restJPA.models.Book;
import com.springboot.restJPA.repository.BookRepository;
import com.springboot.restJPA.response.ListServiceResponse;
import com.springboot.restJPA.response.ServiceResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name="Books")
@RequestMapping("/books")
public class BookController extends BaseController {
    @Autowired
    private final BookRepository repository;

    public BookController(BookRepository repository){
        super();
        this.repository = repository;
    }

    @GetMapping()
    public ResponseEntity<Iterable<Book>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id){
        Optional<Book> boxBook = repository.findById(id);

        if (boxBook.isEmpty()){
            ServiceResponse<Book> serviceResponse = new ServiceResponse<>(HttpStatus.NOT_FOUND.value(), "Book not found!");

            return resultFromServiceResponse(serviceResponse);
        }

        return ResponseEntity.ok(boxBook);
    }

    @GetMapping("/name/{partialName}")
    public ResponseEntity<?> getByName(@PathVariable("partialName") String partialName){
        if (partialName.isBlank()){
            ServiceResponse<Book> serviceResponse = new ServiceResponse<>(HttpStatus.BAD_REQUEST.value(), "Name is required!");

            return resultFromServiceResponse(serviceResponse);
        }

        List<Book> books = repository.findByNameIgnoreCaseContaining(partialName.trim());
        ListServiceResponse<Book> listServiceResponse = new ListServiceResponse<>(HttpStatus.OK.value(), books, null);

        return resultFromServiceResponse(listServiceResponse);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody Book book){
        repository.save(book);

        return resultFromServiceResponse(ServiceResponse.ok());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody Book book){
        Optional<Book> boxBook = repository.findById(id);

        if (boxBook.isEmpty()){
            ServiceResponse<Book> serviceResponse = new ServiceResponse<>(HttpStatus.NOT_FOUND.value(), "Book not found!");

            return resultFromServiceResponse(serviceResponse);
        }

        book.setId(id); // Set ID to avoid new insert
        repository.save(book); // Voiding update

        return resultFromServiceResponse(ServiceResponse.ok());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Optional<Book> boxBook = repository.findById(id);

        if (boxBook.isEmpty()){
            ServiceResponse<Book> serviceResponse = new ServiceResponse<>(HttpStatus.NOT_FOUND.value(), "Book not found!");

            return resultFromServiceResponse(serviceResponse);
        }

        repository.deleteById(id);

        return resultFromServiceResponse(ServiceResponse.ok());
    }
}
