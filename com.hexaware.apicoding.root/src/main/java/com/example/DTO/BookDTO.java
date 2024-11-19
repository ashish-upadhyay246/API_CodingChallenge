package com.example.DTO;

import jakarta.validation.constraints.NotBlank;

public class BookDTO {
	
	private int isbn;
    @NotBlank(message = "Required title.")
    private String title;

    @NotBlank(message = "Required author.")
    private String author;

    @NotBlank(message = "Required year.")
    private String pubYear;

    public BookDTO() {
    }

    public BookDTO(String title, String author, String pubYear) {
        this.title = title;
        this.author = author;
        this.pubYear = pubYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubYear() {
        return pubYear;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
    public int getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "BookDTO [title=" + title + ", author=" + author + ", pubYear=" + pubYear + "]";
    }
}
