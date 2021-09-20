package booksTableManage;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import entityClasses.Author;
import entityClasses.LanguageCode;
import entityClasses.Publisher;
import uri.RestUri;

public class TablesData {
	
	private WebClient client;

	private List<Author> authors = new ArrayList<Author>();
	private List<LanguageCode> languageCodes = new ArrayList<LanguageCode>();
	private List<Publisher> publishers = new ArrayList<Publisher>();
	
	public List<Author> getAuthorsList()
	{
		return this.authors;
	}
	
	public List<LanguageCode> getLanguageCodesList()
	{
		return this.languageCodes;
	}
	
	public List<Publisher> getPublishersList()
	{
		return this.publishers;
	}
	
	public void updateData()
	{
		this.getAllAuthors();
		this.getAllLanguageCodes();
		this.getAllPublishers();
	}
	
	private void getAllAuthors()
	{
		this.client = WebClient.create(RestUri.REST_URI);
		this.client.path("/getAllAuthors").path("").accept("application/json");
		String allAuthorsStrJSON = client.get(String.class);

		Author author = null;

		try {
			JSONArray jsonArr = new JSONArray(allAuthorsStrJSON);
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject object = jsonArr.getJSONObject(i);

				author = new Author();
				author.setAuthorId(Integer.parseInt(object.getString("authorId")));
				author.setAuthor(object.getString("author"));

				this.authors.add(i, author);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void getAllLanguageCodes()
	{
		this.client = WebClient.create(RestUri.REST_URI);
		this.client.path("/getAllLanguageCodes").path("").accept("application/json");
		String allLanguageCodesStrJSON = client.get(String.class);

		LanguageCode languageCode = null;

		try {
			JSONArray jsonArr = new JSONArray(allLanguageCodesStrJSON);
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject object = jsonArr.getJSONObject(i);

				languageCode = new LanguageCode();
				languageCode.setLanguageId(Integer.parseInt(object.getString("languageId")));
				languageCode.setLanguageCode(object.getString("languageCode"));

				this.languageCodes.add(i, languageCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void getAllPublishers()
	{
		this.client = WebClient.create(RestUri.REST_URI);
		this.client.path("/getAllPublishers").path("").accept("application/json");
		String allPublishersStrJSON = client.get(String.class);

		Publisher publisher = null;

		try {
			JSONArray jsonArr = new JSONArray(allPublishersStrJSON);
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject object = jsonArr.getJSONObject(i);

				publisher = new Publisher();
				publisher.setPublisherId(Integer.parseInt(object.getString("publisherId")));
				publisher.setPublisherName(object.getString("publisherName"));

				this.publishers.add(i, publisher);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
