package services;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import entityClasses.Publisher;

@Path("/rest")
public class PublishersService {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.tubitak.rest");

	@SuppressWarnings("finally")
	@GET
	@Path("/insertPublisher/{publisherName}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean insertPublisher(@PathParam("publisherName") String publisherName) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			Publisher publisher = new Publisher();

			publisher.setPublisherName(publisherName);

			em.persist(publisher);
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
	@Path("/getPublisher/{publisherId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPublisher(@PathParam("publisherId") int publisherId) {
		
		Response response = null;

		Publisher publisher = new Publisher();

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			publisher = em.find(Publisher.class, publisherId);
			et.commit();
		} catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
			publisher = null;
		} finally {
			em.close();
		}

		// if there is no author according to the given id value, return null
		if(publisher == null)
			return response; 

		JSONObject jsonPublisher = null;
		try {
			jsonPublisher = new JSONObject();
			jsonPublisher.put("publisherId", publisher.getPublisherId());
			jsonPublisher.put("publisherName", publisher.getPublisherName());
			
			response = Response.status(Status.OK).entity(jsonPublisher.toString()).build();
		} catch (Exception e) {
			System.out.println("error=" + e.getMessage());
		}
		return response;
	}

	@SuppressWarnings("finally")
	@GET
	@Path("/deletePublisher/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean deletePublisher(@PathParam("id") int id) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			Publisher publisher = em.find(Publisher.class, id);

			em.remove(publisher);

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
	@Path("/updatePublisher/{id}/{new_code_value}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean updatePublisher(@PathParam("id") int id, @PathParam("publisherName") String publisherName) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			Publisher publisher = em.find(Publisher.class, id);

			publisher.setPublisherName(publisherName);

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
	@Path("/getAllPublishers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPublishers() {

		Response response = null;
		
		EntityManager em = emf.createEntityManager();

		List<Publisher> results = null;

		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Publisher> cq = cb.createQuery(Publisher.class);
			Root<Publisher> rootEntry = cq.from(Publisher.class);
			CriteriaQuery<Publisher> all = cq.select(rootEntry);
			TypedQuery<Publisher> allQuery = em.createQuery(all);
			results = allQuery.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
			results = null;
		} finally {
			em.close();
		}
		
		if(results == null)
			return null;
		
		JSONArray jsonArrayAllPublishers = new JSONArray();
		
		try {
			for(int i = 0; i < results.size(); i++)
			{
				jsonArrayAllPublishers.put(new JSONObject().put("publisherId", results.get(i).getPublisherId()).put("publisherName", results.get(i).getPublisherName()));
			}
			response = Response.status(Status.OK).entity(jsonArrayAllPublishers.toString()).build();
		} catch (Exception e) {
			System.out.println("error=" + e.getMessage());
			response = null;
		}
		return response;
	}
}
