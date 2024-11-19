package com.example.Service;

import com.example.DTO.BookDTO;
import com.example.Entity.Book;
import com.example.Exceptions.BookNotFoundException;
import com.example.Repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepo rep;

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(book.getTitle(), book.getAuthor(), book.getPubYear());
    }

    public List<BookDTO> getAllBooks() {
        List<Book> b = rep.findAll();
        List<BookDTO> bt = new ArrayList<>();
        for (Book book : b) {
            bt.add(convertToDTO(book));
        }
        return bt;
    }

    public List<BookDTO> getBookByIsbn(int isbn) {
        Optional<Book> book = rep.findById(isbn);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }
        List<BookDTO> bookDTOs = new ArrayList<>();
        book.ifPresent(b -> bookDTOs.add(convertToDTO(b)));
        return bookDTOs;
    }

    public BookDTO addBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getPubYear());
        Book savedBook = rep.save(book);
        return convertToDTO(savedBook);
    }

    public BookDTO updateBook(int isbn, BookDTO bookDTO) {
        Optional<Book> books = rep.findById(isbn);
        if (books.isEmpty()) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }
        Book book = books.get();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPubYear(bookDTO.getPubYear());
        Book update = rep.save(book);
        return convertToDTO(update);
    }

    public boolean deleteBook(int isbn) {
        Optional<Book> books = rep.findById(isbn);
        if (books.isEmpty()) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }
        rep.deleteById(isbn);
        return true;
    }
}
