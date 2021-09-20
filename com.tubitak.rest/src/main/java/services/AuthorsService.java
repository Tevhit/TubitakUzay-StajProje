package services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import entityClasses.Author;

@Path("/rest")
public class AuthorsService {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.tubitak.rest");

	@GET
	@Path("/getAuthor2/{authorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthor(@PathParam("authorId") int id) {
		
		Response response = null;

		Author author = new Author();

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			author = em.find(Author.class, id);
			et.commit();
		} catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
			author = null;
		} finally {
			em.close();
		}

		// if there is no author according to the given id value, return null
		if(author == null)
			return response; 

		JSONObject jsonAuthor = null;
		try {
			jsonAuthor = new JSONObject();
			jsonAuthor.put("authorId", author.getAuthorId());
			jsonAuthor.put("author", author.getAuthor());
			
			response = Response.status(Status.OK).entity(jsonAuthor.toString()).build();
		} catch (Exception e) {
			System.out.println("error=" + e.getMessage());
		}
		return response;
	}

	@SuppressWarnings("finally")
	@GET
	@Path("/deleteAuthor/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean deleteAuthor(@PathParam("id") int id) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			Author author = em.find(Author.class, id);

			em.remove(author);

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
	@Path("/insertAuthor/{author}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean insertAuthor(@PathParam("author") String author) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			Author authors = new Author();
			authors.setAuthor(author);

			em.persist(authors);
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
	@Path("/updateAuthor/{authorId}/{author}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean updateAuthor(@PathParam("authorId") int id, @PathParam("author") String author) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			Author authors = em.find(Author.class, id);

			authors.setAuthor(author);

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
	@Path("/getAllAuthors")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAuthors() {

		Response response = null;
		
		EntityManager em = emf.createEntityManager();

		List<Author> results = null;

		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Author> cq = cb.createQuery(Author.class);
			Root<Author> rootEntry = cq.from(Author.class);
			CriteriaQuery<Author> all = cq.select(rootEntry);
			TypedQuery<Author> allQuery = em.createQuery(all);
			results = allQuery.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
			results = null;
		} finally {
			em.close();
		}
		
		if(results == null)
			return null;
		
		JSONArray jsonArrayAllAuthors = new JSONArray();
		
		try {
			for(int i = 0; i < results.size(); i++)
			{
				jsonArrayAllAuthors.put(new JSONObject().put("authorId", results.get(i).getAuthorId())
						.put("author", results.get(i).getAuthor()));
			}
			response = Response.status(Status.OK).entity(jsonArrayAllAuthors.toString()).build();
		} catch (Exception e) {
			System.out.println("error=" + e.getMessage());
			response = null;
		}
		return response;
	}
}
