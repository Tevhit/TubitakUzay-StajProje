package entityClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Authors")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authorId", updatable = false, nullable = false)
	private int authorId;

	@Column(name = "author")
	private String author;

	public Author() {
		super();
		this.author = "";
	}

	public Author(int authorId, String author) {
		super();
		this.authorId = authorId;
		this.author = author;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Author [authorId=" + authorId + ", author=" + author + "]";
	}
}
