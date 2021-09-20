package entityClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LanguageCodes")
public class LanguageCode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "languageId", updatable = false, nullable = false)
	private int languageId;
	
	@Column(name="languageCode")
	private String languageCode;
	
	public LanguageCode()
	{
		super();
		this.languageCode = "";
	}
	
	public LanguageCode(int languageId, String languageCode)
	{
		super();
		this.languageId = languageId;
		this.languageCode = languageCode;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	@Override
	public String toString() {
		return "LanguageCode [languageId=" + languageId + ", languageCode=" + languageCode + "]";
	}
}
