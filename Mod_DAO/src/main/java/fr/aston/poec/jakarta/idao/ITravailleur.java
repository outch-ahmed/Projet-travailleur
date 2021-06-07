package fr.aston.poec.jakarta.idao;

import java.util.List;

import javax.persistence.EntityManager;

import fr.aston.poec.jakarta.model.Adresse;
import fr.aston.poec.jakarta.model.Entreprise;
import fr.aston.poec.jakarta.model.Geo;
import fr.aston.poec.jakarta.model.Travailleur;

public interface ITravailleur {
	List<Travailleur> allTravailleurs(EntityManager em);
	List<Geo> allGeos(EntityManager em);
	List<Adresse> allAdresses(EntityManager em);
	List<Entreprise> allEntreprises(EntityManager em);
	
	void create(Travailleur travailleur, EntityManager em);
	Travailleur read(Integer id, EntityManager em);
	void update(Travailleur travailleur, EntityManager em);
	void delete(Integer id, EntityManager em);
	
	Geo read(Geo geo, EntityManager em);
	Adresse read(Adresse adresse, EntityManager em);
	Entreprise read(Entreprise entreprise, EntityManager em);
}
