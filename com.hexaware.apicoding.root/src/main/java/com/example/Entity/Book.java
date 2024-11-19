package com.example.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="book")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="isbn")
	private int isbn;
	
    @Column(name = "title", length = 50, nullable = false, unique = true)
    private String title;

    @Column(name = "author", length = 255, nullable = false)
    private String author;
	
    @Column(name = "pubYear", nullable = false)
    private String pubYear;

	public Book(String title, String author, String pubYear) {
		super();
		this.title = title;
		this.author = author;
		this.pubYear = pubYear;
	}
	
    public Book() {}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
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

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", author=" + author + ", pubYear=" + pubYear + "]";
	}
    
    
}



