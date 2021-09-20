package run;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import services.AuthorsService;
import services.BooksService;
import services.LanguageCodesService;
import services.PublishersService;

public class CreateServices {

	public static void main(String[] args) {
		
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();

		sf.setResourceClasses(AuthorsService.class, LanguageCodesService.class, BooksService.class, PublishersService.class);
		
		sf.setResourceProvider(AuthorsService.class, new SingletonResourceProvider(new AuthorsService()));
		sf.setResourceProvider(LanguageCodesService.class, new SingletonResourceProvider(new LanguageCodesService()));
		sf.setResourceProvider(BooksService.class, new SingletonResourceProvider(new BooksService()));
		sf.setResourceProvider(PublishersService.class, new SingletonResourceProvider(new PublishersService()));

		sf.setAddress("http://localhost:9050/test2/");

		sf.create();

	}
}
