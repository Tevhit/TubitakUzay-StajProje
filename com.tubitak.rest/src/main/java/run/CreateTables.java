package run;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CreateTables {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.tubitak.rest");

	public static void main(String[] args) {

		EntityManager em = emf.createEntityManager();

		EntityTransaction et = null;

		try {
			et = em.getTransaction();
			et.begin();
			et.commit();
			System.out.println("Tum tablolar basariyla olusturuldu");
		} catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
			System.out.println("Hata");
		} finally {
			em.close();
		}

	}
}