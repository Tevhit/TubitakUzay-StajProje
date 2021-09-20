package booksTableManage;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import entityClasses.Book;
import uri.RestUri;

public class BooksTableManage {

	private Table booksTable;

	private WebClient client;

	private List<Book> books = new ArrayList<Book>();

	private Book selectedBook = null;

	private Button btnUpdateRecord;
	private Button btnDeleteRecord;
	
	private TablesData tablesData = new TablesData();

	public BooksTableManage(Table booksTable, Button btnUpdateRecord, Button btnDeleteRecord) {
		this.booksTable = booksTable;

		this.btnUpdateRecord = btnUpdateRecord;
		this.btnDeleteRecord = btnDeleteRecord;
	}

	public void prepareBooksTable() {
		this.loadTableColumn();

		this.loadBooksToTable();
	}

	public void loadTableColumn() {
		String[] table_columns = { "Book ID", "Title", "Author", "Language", "Publisher", "Average Rating", "Pages" };
		TableColumn column;
		for (int i = 0; i < table_columns.length; i++) {
			column = new TableColumn(this.booksTable, SWT.NULL);
			column.setText(table_columns[i]);
			this.booksTable.getColumn(i).pack();
			this.booksTable.getColumn(i).setResizable(true);
			this.booksTable.getColumn(i).setMoveable(true);
		}
		this.booksTable.getColumn(0).setWidth(75);
		this.booksTable.getColumn(1).setWidth(450);
		this.booksTable.getColumn(2).setWidth(100);
		this.booksTable.getColumn(3).setWidth(100);
		this.booksTable.getColumn(4).setWidth(100);
		this.booksTable.getColumn(5).setWidth(100);
		this.booksTable.getColumn(6).setWidth(100);
	}

	public void loadBooksToTable() {

		this.booksTable.clearAll();
		this.booksTable.removeAll();

		this.books.clear();

		this.btnUpdateRecord.setEnabled(false);
		this.btnDeleteRecord.setEnabled(false);

		this.client = WebClient.create(RestUri.REST_URI);
		this.client.path("/getAllBooks").path("").accept("application/json");
		String allBooksStrJSON = client.get(String.class);

		Book book = null;

		try {
			JSONArray jsonArr = new JSONArray(allBooksStrJSON);
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject object = jsonArr.getJSONObject(i);

				book = new Book();
				book.setBookId(Integer.parseInt(object.getString("bookId")));
				book.setAuthorId(Integer.parseInt(object.getString("authorId")));
				book.setLanguageId(Integer.parseInt(object.getString("languageId")));
				book.setPublisherId(Integer.parseInt(object.getString("publisherId")));
				book.setTitle(object.getString("title"));
				book.setAverageRating(Double.parseDouble(object.getString("averageRating")));
				book.setNumPages(Integer.parseInt(object.getString("numPages")));

				this.books.add(i, book);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		this.fillBooksTable("");
	}

	public void fillBooksTable(String searchedValue) {

		this.booksTable.clearAll();
		this.booksTable.removeAll();
		
		this.tablesData.updateData();

		String title_ = "";
		String author_ = "";
		String languageCode_ = "";
		String publisher_ = "";
		
		String title_lower = "";
		String author_lower = "";
		String languageCode_lower = "";
		String publisher_lower = "";

		TableItem item;
		for (int i = 0; i < this.books.size(); i++) {

			title_ = String.valueOf(this.books.get(i).getTitle());
			author_ = String.valueOf(this.tablesData.getAuthorsList().get(this.books.get(i).getAuthorId() - 1).getAuthor());
			languageCode_ = String.valueOf(this.tablesData.getLanguageCodesList().get(this.books.get(i).getLanguageId() - 1).getLanguageCode());
			publisher_ = String.valueOf(this.tablesData.getPublishersList().get(this.books.get(i).getPublisherId() - 1).getPublisherName());

			title_lower = title_.toLowerCase();
			author_lower = author_.toLowerCase();
			languageCode_lower = languageCode_.toLowerCase();
			publisher_lower = publisher_.toLowerCase();
			
			searchedValue.toLowerCase();

			if (String.valueOf(this.books.get(i)).contains(searchedValue)
					|| title_lower.contains(searchedValue)
					|| author_lower.contains(searchedValue)
					|| languageCode_lower.contains(searchedValue)
					|| publisher_lower.contains(searchedValue)
					|| String.valueOf(this.books.get(i).getAverageRating()).contains(searchedValue)
					|| String.valueOf(this.books.get(i).getNumPages()).contains(searchedValue)) {

				item = new TableItem(this.booksTable, SWT.NULL);
				item.setText("Item " + i);

				item.setText(0, "" + String.valueOf(this.books.get(i).getBookId()));
				item.setText(1, "" + title_);
				item.setText(2, "" + author_);
				item.setText(3, "" + languageCode_);
				item.setText(4, "" + publisher_);
				item.setText(5, "" + String.valueOf(this.books.get(i).getAverageRating()));
				item.setText(6, "" + String.valueOf(this.books.get(i).getNumPages()));
			}
		}

		this.booksTable.setHeaderVisible(true);
		this.booksTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				String stringSelectionBook = "";
				TableItem[] selection = booksTable.getSelection();
				for (int i = 0; i < selection.length; i++) {
					stringSelectionBook += selection[i].getText() + " ";
				}

				stringSelectionBook = stringSelectionBook.trim();

				int book_id = Integer.parseInt(stringSelectionBook);

				for (int i = 0; i < books.size(); i++) {
					if (books.get(i).getBookId() == book_id)
						selectedBook = books.get(i);
				}

				if (btnUpdateRecord.getEnabled() == false)
					btnUpdateRecord.setEnabled(true);

				if (btnDeleteRecord.getEnabled() == false)
					btnDeleteRecord.setEnabled(true);
			}
		});
	}

	public Book getSelectedBook() {
		return this.selectedBook;
	}
}
