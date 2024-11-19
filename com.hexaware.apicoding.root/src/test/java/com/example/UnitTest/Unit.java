package com.example.UnitTest;

import com.example.Controller.BookController;
import com.example.DTO.BookDTO;
import com.example.Service.BookService;
import com.example.Exceptions.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class Unit {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    //add book
    @Test
    void testAddBook() throws Exception {
        BookDTO bookDTO = new BookDTO("Test Title", "Test Author", "2024");

        when(bookService.addBook(any(BookDTO.class))).thenReturn(bookDTO);

        mockMvc.perform(post("/api/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Title\", \"author\":\"Test Author\", \"pubYear\":\"2024\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.author").value("Test Author"));
    }

    //get all books
    @Test
    void testGetAllBooks() throws Exception {
        List<BookDTO> bookList = new ArrayList<>();
        bookList.add(new BookDTO("Test Title", "Test Author", "2024"));

        when(bookService.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(get("/api/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Test Title"));
    }

    //get book by id
    @Test
    void testGetBookByIsbn() throws Exception {
        BookDTO bookDTO = new BookDTO("Test Title", "Test Author", "2024");
        bookDTO.setIsbn(123456);

        when(bookService.getBookByIsbn(123456)).thenReturn(List.of(bookDTO));

        mockMvc.perform(get("/api/getBook/{isbn}", 123456))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value(123456));
    }

    //update book
    @Test
    void testUpdateBook() throws Exception {
        BookDTO bookDTO = new BookDTO("Updated Title", "Updated Author", "2025");
        bookDTO.setIsbn(123456);

        when(bookService.updateBook(eq(123456), any(BookDTO.class))).thenReturn(bookDTO);

        mockMvc.perform(put("/api/updateBook/{isbn}", 123456)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Title\", \"author\":\"Updated Author\", \"pubYear\":\"2025\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    //delete book
    @Test
    void testDeleteBook() throws Exception {
        when(bookService.deleteBook(123456)).thenReturn(true);

        mockMvc.perform(delete("/api/deleteBook/{isbn}", 123456))
                .andExpect(status().isNoContent());
    }
}
