package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import entityClasses.Book;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Path("/rest")
public class BooksService {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.tubitak.rest");

	@SuppressWarnings("finally")
	@GET
	@Path("/insertBook/{authorId}/{languageId}/{publisherId}/{title}/{averageRating}/{numPages}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean insertBook(@PathParam("authorId") int authorId, 
			@PathParam("languageId") int languageId, 
			@PathParam("publisherId") int publisherId, 
			@PathParam("title") String title, 
			@PathParam("averageRating") Double averageRating, 
			@PathParam("numPages") int numPages) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			Book mbook = new Book();

			mbook.setAuthorId(authorId);
			mbook.setLanguageId(languageId);
			mbook.setPublisherId(publisherId);
			mbook.setTitle(title);
			mbook.setAverageRating(averageRating);
			mbook.setNumPages(numPages);
			
			em.persist(mbook);
            
			et.commit();
		} catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
			result = false;
		} finally {
			em.close();
			return result;
		}
	}
	
	@GET
	@Path("/getBook/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBook(@PathParam("id") int id) {
		
		Response response = null;

		Book book = new Book();

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			book = em.find(Book.class, id);
			et.commit();
		} catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
			book = null;
		} finally {
			em.close();
		}

		// if there is no author according to the given id value, return null
		if(book == null)
			return response; 

		JSONObject jsonBook = null;
		try {
			jsonBook = new JSONObject();
			jsonBook.put("bookId", book.getBookId());
			jsonBook.put("authorId", book.getAuthorId());
			jsonBook.put("languageId", book.getLanguageId());
			jsonBook.put("publisherId", book.getPublisherId());
			jsonBook.put("title", book.getTitle());
			jsonBook.put("averageRating", book.getAverageRating());
			jsonBook.put("numPages", book.getNumPages());
			
			response = Response.status(Status.OK).entity(jsonBook.toString()).build();
		} catch (Exception e) {
			System.out.println("error=" + e.getMessage());
		}
		return response;
	}
	
	@SuppressWarnings("finally")
	@GET
	@Path("/deleteBook/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean deleteBook(@PathParam("id") int id) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			Book book = em.find(Book.class, id);

			em.remove(book);

			et.commit();
		} catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
			result = false;
		} finally {
			em.close();
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	@GET
	@Path("/updateBook/{bookId}/{authorId}/{languageId}/{publisherId}/{title}/{averageRating}/{numPages}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean updateBook(@PathParam("bookId") int bookId,
			@PathParam("authorId") int authorId, 
			@PathParam("languageId") int languageId, 
			@PathParam("publisherId") int publisherId, 
			@PathParam("title") String title, 
			@PathParam("averageRating") Double averageRating, 
			@PathParam("numPages") int numPages) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			Book book = em.find(Book.class, bookId);

			book.setAuthorId(authorId);
			book.setLanguageId(languageId);
			book.setPublisherId(publisherId);
			book.setTitle(title);
			book.setAverageRating(averageRating);
			book.setNumPages(numPages);

			et.commit();
		} catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
			result = false;
		} finally {
			em.close();
			return result;
		}
	}
	
	@GET
	@Path("/getAllBooks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBooks() {

		Response response = null;
		
		EntityManager em = emf.createEntityManager();

		List<Book> results = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Book> cq = cb.createQuery(Book.class);
			Root<Book> rootEntry = cq.from(Book.class);
			CriteriaQuery<Book> all = cq.select(rootEntry);
			TypedQuery<Book> allQuery = em.createQuery(all);
			results = allQuery.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
			results = null;
		} finally {
			em.close();
		}
		
		if(results == null)
			return null;
		
		JSONArray jsonArrayAllBooks = new JSONArray();
		
		try {
			for(int i = 0; i < results.size(); i++)
			{
				jsonArrayAllBooks.put(new JSONObject().put("bookId", results.get(i).getBookId())
													.put("authorId", results.get(i).getAuthorId())
													.put("languageId", results.get(i).getLanguageId())
													.put("publisherId", results.get(i).getPublisherId())
													.put("title", results.get(i).getTitle())
													.put("averageRating", results.get(i).getAverageRating())
													.put("numPages", results.get(i).getNumPages()));
			}
			response = Response.status(Status.OK).entity(jsonArrayAllBooks.toString()).build();
		} catch (Exception e) {
			System.out.println("error=" + e.getMessage());
			response = null;
		}
		return response;
	}
}
