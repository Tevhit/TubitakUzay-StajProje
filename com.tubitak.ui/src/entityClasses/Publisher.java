package entityClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Publishers")
public class Publisher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "publisherId", updatable = false, nullable = false)
	private int publisherId;
	
	@Column(name="publisherName")
	private String publisherName;
	
	public Publisher()
	{
		super();
		this.publisherName = "";
	}
	
	public Publisher(int publisherId, String publisherName)
	{
		super();
		this.publisherId = publisherId;
		this.publisherName = publisherName;
	}

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	@Override
	public String toString() {
		return "Publisher [publisherId=" + publisherId + ", publisherName=" + publisherName + "]";
	}
}
