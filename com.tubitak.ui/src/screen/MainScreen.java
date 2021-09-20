package screen;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import tabs.ListBooksTab;

public class MainScreen {

	@Inject
	public MainScreen(Composite parent) {
		
		//System.out.println(org.hibernate.Version.getVersionString());

		GridLayout gridLayout = new GridLayout();
		parent.setLayout(gridLayout);

		final TabFolder tabFolder = new TabFolder(parent, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gridData = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
		gridData.heightHint = 800;
		gridData.widthHint = 1500;
		tabFolder.setSize(1200, 900);
		tabFolder.setLayoutData(gridData);

		TabItem tabListBooks = new TabItem(tabFolder, SWT.NULL);
		tabListBooks.setText("List Books");

		Composite compositeForTabListBooks = new Composite(tabFolder, SWT.NONE);

		ListBooksTab listBooksTab = new ListBooksTab(tabFolder, compositeForTabListBooks, tabListBooks);
		listBooksTab.prepareTab();
	}
}
