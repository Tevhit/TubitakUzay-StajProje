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

import entityClasses.LanguageCode;

@Path("/rest")
public class LanguageCodesService {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.tubitak.rest");

	@SuppressWarnings("finally")
	@GET
	@Path("/insertLanguageCode/{code}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean insertLanguageCode(@PathParam("code") String code) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			LanguageCode languageCode = new LanguageCode();
			languageCode.setLanguageCode(code);

			em.persist(languageCode);
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
	@Path("/getLanguageCode/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLanguageCode(@PathParam("id") int id) {
		
		Response response = null;

		LanguageCode languageCode = new LanguageCode();

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			languageCode = em.find(LanguageCode.class, id);
			et.commit();
		} catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
			languageCode = null;
		} finally {
			em.close();
		}

		// if there is no author according to the given id value, return null
		if(languageCode == null)
			return response; 

		JSONObject jsonLanguageCode = null;
		try {
			jsonLanguageCode = new JSONObject();
			jsonLanguageCode.put("languageId", languageCode.getLanguageId());
			jsonLanguageCode.put("languageCode", languageCode.getLanguageCode());
			
			response = Response.status(Status.OK).entity(jsonLanguageCode.toString()).build();
		} catch (Exception e) {
			System.out.println("error=" + e.getMessage());
		}
		return response;
	}

	@SuppressWarnings("finally")
	@GET
	@Path("/deleteLanguageCode/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean deleteLanguageCode(@PathParam("id") int id) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			LanguageCode languageCode = em.find(LanguageCode.class, id);

			em.remove(languageCode);

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
	@Path("/updateLanguageCode/{id}/{new_code_value}")
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean updateLanguageCode(@PathParam("id") int id, @PathParam("new_code_value") String new_code_value) {

		Boolean result = true;

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();

			LanguageCode languageCode = em.find(LanguageCode.class, id);

			languageCode.setLanguageCode(new_code_value);

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
	@Path("/getAllLanguageCodes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLanguageCodes() {

		Response response = null;
		
		EntityManager em = emf.createEntityManager();

		List<LanguageCode> results = null;

		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<LanguageCode> cq = cb.createQuery(LanguageCode.class);
			Root<LanguageCode> rootEntry = cq.from(LanguageCode.class);
			CriteriaQuery<LanguageCode> all = cq.select(rootEntry);
			TypedQuery<LanguageCode> allQuery = em.createQuery(all);
			results = allQuery.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
			results = null;
		} finally {
			em.close();
		}
		
		if(results == null)
			return null;
		
		JSONArray jsonArrayAllLanguageCodes = new JSONArray();
		
		try {
			for(int i = 0; i < results.size(); i++)
			{
				jsonArrayAllLanguageCodes.put(new JSONObject().put("languageId", results.get(i).getLanguageId()).put("languageCode", results.get(i).getLanguageCode()));
			}
			response = Response.status(Status.OK).entity(jsonArrayAllLanguageCodes.toString()).build();
		} catch (Exception e) {
			System.out.println("error=" + e.getMessage());
			response = null;
		}
		return response;
	}
}
