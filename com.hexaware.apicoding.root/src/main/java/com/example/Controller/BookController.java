package com.example.Controller;

import com.example.DTO.BookDTO;
import com.example.Service.BookService;
import com.example.Exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService ser;

    //add a book
    @PostMapping("/addBook")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO savedBook = ser.addBook(bookDTO);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);        
    }
        
    //get all books
    @GetMapping("/getAll")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = ser.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    //get books by ISBN
    @GetMapping("/getBook/{isbn}")
    public ResponseEntity<List<BookDTO>> getBookByIsbn(@PathVariable int isbn) {
        try {
            List<BookDTO> book = ser.getBookByIsbn(isbn);
            return ResponseEntity.ok(book);
        } catch (BookNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new BookDTO())); // Or appropriate error response
        }
    }

    //update a book
    @PutMapping("/updateBook/{isbn}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int isbn, @Valid @RequestBody BookDTO bookDTO) {
        try {
            BookDTO updatedBook = ser.updateBook(isbn, bookDTO);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (BookNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //delete a book
    @DeleteMapping("/deleteBook/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable int isbn) {
        try {
            boolean isDeleted = ser.deleteBook(isbn);
            return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (BookNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
