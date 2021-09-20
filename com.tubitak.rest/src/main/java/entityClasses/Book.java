package entityClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId", updatable = false, nullable = false)
	private int bookId;

	@Column(name="authorId")
	private int authorId;
	
	@Column(name="languageId")
	private int languageId;
	
	@Column(name="publisherId")
	private int publisherId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="averageRating")
	private Double averageRating;
	
	@Column(name="numPages")
	private int numPages;
	
	public Book() {
		super();
		this.authorId = 0;
		this.languageId = 0;
		this.publisherId = 0;
		this.title = "";
		this.averageRating = 0.0;
		this.numPages = 0;
	}

	public Book(int bookId, int authorId, int languageId, int publisherId, String title, Double averageRating,
			int numPages) {
		super();
		this.bookId = bookId;
		this.authorId = authorId;
		this.languageId = languageId;
		this.publisherId = publisherId;
		this.title = title;
		this.averageRating = averageRating;
		this.numPages = numPages;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public int getNumPages() {
		return numPages;
	}

	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}	
}
