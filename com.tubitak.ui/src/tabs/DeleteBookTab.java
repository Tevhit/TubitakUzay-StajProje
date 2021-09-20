package tabs;

import org.apache.cxf.jaxrs.client.WebClient;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import booksTableManage.BooksTableManage;
import booksTableManage.TablesData;
import entityClasses.Book;
import uri.RestUri;

public class DeleteBookTab {
	private TabFolder tabFolder;

	private Composite composite;

	private TabItem tabItem;

	private Book book;

	private BooksTableManage booksTableManage;
	
	private TablesData tablesData = new TablesData();

	public DeleteBookTab(final TabFolder tabFolder, final Composite composite, final TabItem tabItem, Book book,
			BooksTableManage booksTableManage) {
		this.tabFolder = tabFolder;
		this.composite = composite;
		this.tabItem = tabItem;
		this.tabItem.setControl(this.composite);
		this.booksTableManage = booksTableManage;
		this.book = book;
	}

	public void prepareUI() {
		
		this.tablesData.updateData();

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginTop = 50;
		gridLayout.marginLeft = 50;
		this.composite.setLayout(gridLayout);

		Label lblTitleInfo = new Label(this.composite, SWT.NULL);
		lblTitleInfo.setText("Title :");

		Label lblTitle = new Label(this.composite, SWT.NULL);
		lblTitle.setText(book.getTitle());

		Label lblAuthorInfo = new Label(this.composite, SWT.NULL);
		lblAuthorInfo.setText("Author :");

		Label lblAuthor = new Label(this.composite, SWT.NULL);
		lblAuthor.setText(this.tablesData.getAuthorsList().get(book.getAuthorId() - 1).getAuthor());

		Label lblLanguageInfo = new Label(this.composite, SWT.NULL);
		lblLanguageInfo.setText("Language :");

		Label lblLanguage = new Label(this.composite, SWT.NULL);
		lblLanguage.setText(this.tablesData.getLanguageCodesList().get(book.getLanguageId() - 1).getLanguageCode());

		Label lblPublisherInfo = new Label(this.composite, SWT.NULL);
		lblPublisherInfo.setText("Publisher :");

		Label lblPublisher = new Label(this.composite, SWT.NULL);
		lblPublisher.setText(this.tablesData.getPublishersList().get(book.getPublisherId() - 1).getPublisherName());

		Label lblAverageRatingInfo = new Label(this.composite, SWT.NULL);
		lblAverageRatingInfo.setText("AverageRating :");

		Label lblAverageRating = new Label(this.composite, SWT.NULL);
		lblAverageRating.setText(String.valueOf(book.getAverageRating()));

		Label lblPagesInfo = new Label(this.composite, SWT.NULL);
		lblPagesInfo.setText("Pages :");

		Label lblPages = new Label(this.composite, SWT.NULL);
		lblPages.setText(String.valueOf(book.getNumPages()));

		Label lblempty1 = new Label(this.composite, SWT.NULL);
		Label lblempty2 = new Label(this.composite, SWT.NULL);

		Label lblQuestion = new Label(this.composite, SWT.NULL);
		lblQuestion.setText("Are you sure to delete?");

		Label lblempty3 = new Label(this.composite, SWT.NULL);

		Button btnYes = new Button(this.composite, SWT.PUSH);
		btnYes.setText("Yes");

		Button btnNo = new Button(this.composite, SWT.PUSH);
		btnNo.setText("No");

		btnYes.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				deleteBook();
			}
		});

		btnNo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				closeTab();
			}
		});
	}

	private void closeTab() {
		composite.dispose();
		tabItem.dispose();
		tabFolder.setSelection(0);
	}

	private void deleteBook() {
		WebClient client = WebClient.create(RestUri.REST_URI);
		client.path("/deleteBook").path(this.book.getBookId() + "/").accept("text/plain");
		String allBooksStrJSON = client.get(String.class);
		System.out.println(allBooksStrJSON);
		this.booksTableManage.loadBooksToTable();
		this.closeTab();
	}
}
