package tabs;

import org.apache.cxf.jaxrs.client.WebClient;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import booksTableManage.BooksTableManage;
import booksTableManage.TablesData;
import entityClasses.Book;
import uri.RestUri;

public class UpdateBookTab {

	private TabFolder tabFolder;

	private Composite composite;

	private TabItem tabItem;

	private Book book;

	private BooksTableManage booksTableManage;

	private TablesData tablesData = new TablesData();

	private Text txtTitle, txtAuthor, txtLanguage, txtPublisher, txtAverageRating, txtPages;

	private Label lblError;

	public UpdateBookTab(final TabFolder tabFolder, final Composite composite, final TabItem tabItem,
			BooksTableManage booksTableManage, Book book) {
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

		/// Row 1:

		Label lblTitleInfo = new Label(this.composite, SWT.NULL);
		lblTitleInfo.setText("Title :");

		GridData data = new GridData(25, SWT.CENTER, false, false);
		data.widthHint = 300;
		this.txtTitle = new Text(this.composite, SWT.BORDER);
		this.txtTitle.setLayoutData(data);
		this.txtTitle.setText(this.book.getTitle());

		//////////////////////////////////////////////////////////

		/// Row 2:

		Label lblAuthorInfo = new Label(this.composite, SWT.NULL);
		lblAuthorInfo.setText("Author :");

		this.txtAuthor = new Text(this.composite, SWT.BORDER);
		this.txtAuthor.setLayoutData(data);
		this.txtAuthor.setText(this.tablesData.getAuthorsList().get(book.getAuthorId() - 1).getAuthor());

		//////////////////////////////////////////////////////////

		/// Row 3:

		Label lblLanguageInfo = new Label(this.composite, SWT.NULL);
		lblLanguageInfo.setText("Language :");

		this.txtLanguage = new Text(this.composite, SWT.BORDER);
		this.txtLanguage.setLayoutData(data);
		this.txtLanguage.setText(this.tablesData.getLanguageCodesList().get(book.getLanguageId() - 1).getLanguageCode());

		//////////////////////////////////////////////////////////

		/// Row 4:

		Label lblPublisherInfo = new Label(this.composite, SWT.NULL);
		lblPublisherInfo.setText("Publisher :");

		this.txtPublisher = new Text(this.composite, SWT.BORDER);
		this.txtPublisher.setLayoutData(data);
		this.txtPublisher.setText(this.tablesData.getPublishersList().get(book.getPublisherId() - 1).getPublisherName());

		//////////////////////////////////////////////////////////

		/// Row 5:

		Label lblAverageRatingInfo = new Label(this.composite, SWT.NULL);
		lblAverageRatingInfo.setText("AverageRating :");

		this.txtAverageRating = new Text(this.composite, SWT.BORDER);
		this.txtAverageRating.setLayoutData(data);
		this.txtAverageRating.setText(String.valueOf(this.book.getAverageRating()));

		//////////////////////////////////////////////////////////

		/// Row 6:

		Label lblPagesInfo = new Label(this.composite, SWT.NULL);
		lblPagesInfo.setText("Pages :");

		this.txtPages = new Text(this.composite, SWT.BORDER);
		this.txtPages.setLayoutData(data);
		this.txtPages.setText(String.valueOf(this.book.getNumPages()));

		//////////////////////////////////////////////////////////

		/// Row 7:

		Label lblempty1 = new Label(this.composite, SWT.NULL);
		Label lblempty2 = new Label(this.composite, SWT.NULL);

		//////////////////////////////////////////////////////////

		/// Row 8:

		Label lblQuestion = new Label(this.composite, SWT.NULL);
		lblQuestion.setText("Are you sure to update?");

		Label lblempty3 = new Label(this.composite, SWT.NULL);

		//////////////////////////////////////////////////////////

		Button btnYes = new Button(this.composite, SWT.PUSH);
		btnYes.setText("Yes");

		Button btnNo = new Button(this.composite, SWT.PUSH);
		btnNo.setText("No");

		//////////////////////////////////////////////////////////

		/// Row 9:

		Label lblempty4 = new Label(this.composite, SWT.NULL);

		this.lblError = new Label(this.composite, SWT.NULL);
		lblError.setText("Please check your inputs...");
		lblError.setVisible(false);
		this.lblError.setLayoutData(data);

		//////////////////////////////////////////////////////////

		btnYes.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateBook();
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
	
	private void updateBook() {

		if (this.checkInputs() == true) {

			WebClient client = WebClient.create(RestUri.REST_URI);
			client.path("/updateBook").path(
					this.book.getBookId()
					+ "/"
					+ this.getAuthorIdForInsert(this.txtAuthor.getText())
					+ "/"
					+ this.getLanguageIdForInsert(this.txtLanguage.getText())
					+ "/" 
					+ this.getPublisherIdForInsert(this.txtPublisher.getText())
					+ "/" 
					+ this.txtTitle.getText() 
					+ "/" 
					+ Double.parseDouble(this.txtAverageRating.getText()) 
					+ "/"
					+ Integer.parseInt(this.txtPages.getText()) 
					+ "/").accept("text/plain");
			String allBooksStrJSON = client.get(String.class);
			System.out.println(allBooksStrJSON);
			this.booksTableManage.loadBooksToTable();
			this.closeTab();
		}
	}

	private Boolean checkInputs() {
		this.lblError.setVisible(false);

		if (this.txtTitle.getText().trim().isEmpty()) {
			this.lblError.setVisible(true);
			this.lblError.setText("Please enter a title..");
			return false;
		} else if (this.txtAuthor.getText().trim().isEmpty()) {
			this.lblError.setVisible(true);
			this.lblError.setText("Please enter a author..");
			return false;
		} else if (this.txtLanguage.getText().trim().isEmpty()) {
			this.lblError.setVisible(true);
			this.lblError.setText("Please enter a language code..");
			return false;
		} else if (this.txtPublisher.getText().trim().isEmpty()) {
			this.lblError.setVisible(true);
			this.lblError.setText("Please enter a publisher..");
			return false;
		}

		try {
			double num = Double.parseDouble(this.txtAverageRating.getText());
		} catch (NumberFormatException e) {
			this.lblError.setVisible(true);
			this.lblError.setText("Please enter a double number for average rating..");
			return false;
		}
		
		try {
			int num = Integer.parseInt(this.txtPages.getText());
		} catch (NumberFormatException e) {
			this.lblError.setVisible(true);
			this.lblError.setText("Please enter a integer number for pages count..");
			return false;
		}

		return true;
	}
	
	private int getLanguageIdForInsert(String inserted_language_code)
	{
		int size = this.tablesData.getLanguageCodesList().size();
		
		int id = -1;
		
		for(int i = 0; i < size; i++)
		{
			if(this.tablesData.getLanguageCodesList().get(i).getLanguageCode().equals(inserted_language_code))
				id = this.tablesData.getLanguageCodesList().get(i).getLanguageId();
		}
		
		if(id == -1)
		{
			id = size + 1;
			this.insertNewLanguage(inserted_language_code);
		}
		return id;
	}
	
	
	private void insertNewLanguage(String language_code)
	{
		WebClient client = WebClient.create(RestUri.REST_URI);
		client.path("/insertLanguageCode").path(language_code + "/").accept("text/plain");
		String str = client.get(String.class);
		System.out.println(str);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int getAuthorIdForInsert(String inserted_author)
	{
		int size = this.tablesData.getAuthorsList().size();
		
		int id = -1;
		
		for(int i = 0; i < size; i++)
		{
			if(this.tablesData.getAuthorsList().get(i).getAuthor().equals(inserted_author))
				id = this.tablesData.getAuthorsList().get(i).getAuthorId();
		}
		
		if(id == -1)
		{
			id = size + 1;
			this.insertNewAuthor(inserted_author);
		}
		return id;
	}
	
	
	private void insertNewAuthor(String inserted_author)
	{
		WebClient client = WebClient.create(RestUri.REST_URI);
		client.path("/insertAuthor").path(inserted_author + "/").accept("text/plain");
		String str = client.get(String.class);
		System.out.println(str);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int getPublisherIdForInsert(String inserted_publisher)
	{
		int size = this.tablesData.getPublishersList().size();
		
		int id = -1;
		
		for(int i = 0; i < size; i++)
		{
			if(this.tablesData.getPublishersList().get(i).getPublisherName().equals(inserted_publisher))
				id = this.tablesData.getPublishersList().get(i).getPublisherId();
		}
		
		if(id == -1)
		{
			id = size + 1;
			this.insertNewPublisher(inserted_publisher);
		}
		return id;
	}
	
	
	private void insertNewPublisher(String inserted_publisher)
	{
		WebClient client = WebClient.create(RestUri.REST_URI);
		client.path("/insertPublisher").path(inserted_publisher + "/").accept("text/plain");
		String str = client.get(String.class);
		System.out.println(str);
	}
}
