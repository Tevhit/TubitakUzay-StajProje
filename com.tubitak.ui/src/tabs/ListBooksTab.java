package tabs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import booksTableManage.BooksTableManage;
import entityClasses.Book;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;

public class ListBooksTab {

	private TabFolder tabFolder;

	private Composite composite;

	private TabItem tabItem;

	private BooksTableManage booksTableManage;

	private Table booksTable;

	private Book selectedBook = null;

	private Button btnNewRecord;
	private Button btnUpdateRecord;
	private Button btnDeleteRecord;

	public ListBooksTab(TabFolder tabFolder, Composite composite, TabItem tabItem) {
		this.tabFolder = tabFolder;
		this.composite = composite;
		this.tabItem = tabItem;
		this.tabItem.setControl(composite);
		this.composite.setLayout(new GridLayout(2, false));
	}

	public void prepareTab() {
		this.createUI();

		this.booksTableManage = new BooksTableManage(this.booksTable, this.btnUpdateRecord, this.btnDeleteRecord);
		this.booksTableManage.prepareBooksTable();
	}

	private void createUI() {
		this.createButtons();
		this.createSearchPart();
		this.createBooksTable();
	}

	private void createButtons() {

		GridLayout gridLayout = new GridLayout(5, false);
		gridLayout.marginTop = 50;
		gridLayout.marginLeft = 50;
		this.composite.setLayout(gridLayout);

		this.btnNewRecord = new Button(this.composite, SWT.PUSH | SWT.LEFT | SWT.TOP);
		this.btnNewRecord.setText("Add New Book");

		this.btnUpdateRecord = new Button(this.composite, SWT.PUSH | SWT.LEFT | SWT.TOP);
		this.btnUpdateRecord.setText("Update Book");

		this.btnDeleteRecord = new Button(this.composite, SWT.PUSH | SWT.LEFT | SWT.TOP);
		this.btnDeleteRecord.setText("Delete Book");

		this.btnUpdateRecord.setEnabled(false);
		this.btnDeleteRecord.setEnabled(false);

		this.btnNewRecord.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				createInsertTab();
			}
		});

		this.btnDeleteRecord.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				createDeleteTab();
			}
		});

		this.btnUpdateRecord.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				createUpdateTab();
			}
		});
	}

	private void createSearchPart() {
		Label lblSearchInfo = new Label(this.composite, SWT.NULL);
		lblSearchInfo.setText("Search : ");

		final Text txtSearch = new Text(this.composite, SWT.BORDER);
		txtSearch.setText("");

		GridData data = new GridData(25, SWT.CENTER, false, false);
		data.widthHint = 300;

		txtSearch.setLayoutData(data);
		txtSearch.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				booksTableManage.fillBooksTable(txtSearch.getText());
			}
		});
	}

	private void createDeleteTab() {
		this.selectedBook = this.booksTableManage.getSelectedBook();

		TabItem tabDeleteBook = new TabItem(tabFolder, SWT.NULL);
		tabDeleteBook.setText("Delete Book");

		Composite compositeForTabDeleteBook = new Composite(tabFolder, SWT.NONE);

		DeleteBookTab deleteBookTab = new DeleteBookTab(this.tabFolder, compositeForTabDeleteBook, tabDeleteBook,
				this.selectedBook, this.booksTableManage);
		deleteBookTab.prepareUI();

		tabFolder.setSelection(1);
	}

	private void createInsertTab() {
		TabItem tabInsertBook = new TabItem(tabFolder, SWT.NULL);
		tabInsertBook.setText("Insert Book");

		Composite compositeForTabInsertBook = new Composite(tabFolder, SWT.NONE);

		AddBookTab addBookTab = new AddBookTab(this.tabFolder, compositeForTabInsertBook, tabInsertBook,
				this.booksTableManage);
		addBookTab.prepareUI();

		tabFolder.setSelection(1);
	}

	private void createUpdateTab() {
		this.selectedBook = this.booksTableManage.getSelectedBook();
		
		TabItem tabUpdateBook = new TabItem(tabFolder, SWT.NULL);
		tabUpdateBook.setText("Update Book");

		Composite compositeForTabUpdateBook = new Composite(tabFolder, SWT.NONE);

		UpdateBookTab updateBookTab = new UpdateBookTab(this.tabFolder, compositeForTabUpdateBook, tabUpdateBook,
				this.booksTableManage, this.selectedBook);
		updateBookTab.prepareUI();

		tabFolder.setSelection(1);
	}

	private void createBooksTable() {

		this.booksTable = new Table(this.composite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.LEFT, SWT.TOP, false, false, 5, 1);
		gd_table.heightHint = 500;
		gd_table.widthHint = 1200;
		this.booksTable.setLayoutData(gd_table);
	}

}
