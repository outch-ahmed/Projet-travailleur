package fr.aston.poec.jakarta.resources;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.aston.poec.jakarta.dao.impls.TravailleurDao;
import fr.aston.poec.jakarta.model.Travailleur;

public class TravailleurRessourceTest {
	TravailleurResource ressource = new TravailleurResource();

	
//	EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSIST_PU");
//	EntityManager em = emf.createEntityManager();
	
	

	@Test
	void testInsertion() {
		Travailleur travailleur = new Travailleur("ouch", "prenom", "ooua@gmail.fr", LocalDate.of(1988, 1, 06));
		List<Travailleur> liste = ressource.allTravailleurs();
//		ressource.create(travailleur);
	}

}
