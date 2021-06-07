package fr.aston.poec.jakarta.dao.impls;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import fr.aston.poec.jakarta.idao.ITravailleur;
import fr.aston.poec.jakarta.model.Adresse;
import fr.aston.poec.jakarta.model.Entreprise;
import fr.aston.poec.jakarta.model.Geo;
import fr.aston.poec.jakarta.model.JpaUtils;
import fr.aston.poec.jakarta.model.Travailleur;

@Stateful
public class TravailleurDao implements ITravailleur {

	@Override
	public List<Travailleur> allTravailleurs(EntityManager em) {
		TypedQuery<Travailleur> query = em.createQuery("select t from Travailleur t", Travailleur.class);
		return query.getResultList();
	}

	@Override
	public List<Geo> allGeos(EntityManager em) {
		TypedQuery<Geo> query = em.createQuery("select g from Geo g", Geo.class);
		return query.getResultList();
	}

	@Override
	public List<Adresse> allAdresses(EntityManager em) {
		TypedQuery<Adresse> query = em.createQuery("select a from Adresse a", Adresse.class);
		return query.getResultList();
	}

	@Override
	public List<Entreprise> allEntreprises(EntityManager em) {
		TypedQuery<Entreprise> query = em.createQuery("select g from Entreprise g", Entreprise.class);
		return query.getResultList();
	}

	@Override
	public void create(Travailleur travailleur, EntityManager em) {
		try {
			UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
			Geo geo = null;
			// pour eviter un nullExceptionPointer en cas ou .get adresse est null
			if (travailleur.getAdresse() != null) {
				geo = travailleur.getAdresse().getGeo();
			}
			if (geo != null) {
				if (allGeos(em).contains(geo)) {
					Geo geoDb = read(geo, em);
					geo = geoDb;
				} else {
					transaction.begin();
					em.persist(geo);
					transaction.commit();
				}
			}
			Adresse adresse = travailleur.getAdresse();
			if (adresse != null) {
				if (allAdresses(em).contains(adresse)) {
					Adresse adresseDb = read(adresse, em);
					adresse = adresseDb;
				} else {
					adresse.setGeo(geo);
					transaction.begin();
					em.persist(adresse);
					transaction.commit();
				}
			}
			Entreprise entreprise = travailleur.getEntreprise();
			if (entreprise != null) {
				if (allEntreprises(em).contains(entreprise)) {
					Entreprise entrepriseDb = read(entreprise, em);
					entreprise = entrepriseDb;
				} else {
					transaction.begin();
					em.persist(entreprise);
					entreprise = em.merge(entreprise);
					transaction.commit();
				}
			}
			travailleur.setAdresse(adresse);
			travailleur.setEntreprise(entreprise);
			if (!allTravailleurs(em).contains(travailleur)) {
				transaction.begin();
				em.persist(travailleur);
				transaction.commit();
			}
			else {
				transaction.begin();
				em.merge(travailleur);
				transaction.commit();
			}
		} catch (NamingException | NotSupportedException | SystemException | SecurityException | IllegalStateException
				| RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		} finally {

		}
	}

	@SuppressWarnings("finally")
	@Override
	public Travailleur read(Integer id, EntityManager em) {
		Travailleur travailleur = null;
		try {
			travailleur = em.find(Travailleur.class, id);
		} catch (Exception e) {
		} finally {
			return travailleur;
		}
	}

	@Override
	public void update(Travailleur travailleur, EntityManager em) {

		Travailleur travailleurDb = read(travailleur.getId(), em);
		try {
			UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
			if (travailleurDb.getAdresse() != null && travailleur.getAdresse() != null ) {
				Geo geoOld = travailleurDb.getAdresse().getGeo();
				Geo geoNew = travailleur.getAdresse().getGeo();
				if (geoNew != null && geoNew.equals(geoOld)) {
					geoNew = read(geoOld, em);
					travailleur.getAdresse().setGeo(geoNew);
				}
			}

			Adresse adresseOld = travailleurDb.getAdresse();
			Adresse adresseNew = travailleur.getAdresse();

			if (adresseNew == null) {
				adresseNew = read(adresseOld, em);
				travailleur.setAdresse(adresseNew);
			}
			if (adresseNew != null)
			{
				if(adresseNew.equals(adresseOld))
				{
					adresseNew = read(adresseOld, em);
					travailleur.setAdresse(adresseNew);
				}
			}
					
			Entreprise entrepriseOld = travailleurDb.getEntreprise();
			Entreprise entrepriseNew = travailleur.getEntreprise();

			if (entrepriseNew == null) {
				entrepriseNew = read(entrepriseOld, em);
				travailleur.setEntreprise(entrepriseNew);
			}
			if (entrepriseNew != null) {
				if(entrepriseNew.equals(entrepriseOld))
				{
					entrepriseNew = read(entrepriseOld, em);
					travailleur.setEntreprise(entrepriseNew);
				}
			}
			
			transaction.begin();
			travailleur.setVersion(travailleurDb.getVersion());
			travailleur = em.merge(travailleur);
			transaction.commit();
		} catch (NamingException | NotSupportedException | SystemException | SecurityException | IllegalStateException
				| RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void delete(Integer id, EntityManager em) {
		Travailleur travailleur = read(id, em);
		if (travailleur != null) {
			try {
				UserTransaction transaction = (UserTransaction) new InitialContext()
						.lookup("java:comp/UserTransaction");
				transaction.begin();
				em.remove(em.merge(travailleur));
				transaction.commit();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Geo read(Geo geo, EntityManager em) {
		Geo geoRetour = null;
		try {
			TypedQuery<Geo> query = em.createQuery("SELECT g FROM Geo g  WHERE g.lat = ?1 AND g.lng = ?2", Geo.class);
			query.setParameter(1, geo.getLat());
			query.setParameter(2, geo.getLng());
			geoRetour = query.getSingleResult();
		} catch (Exception e) {
		} finally {
			return geoRetour;
		}

	}

	@SuppressWarnings("finally")
	@Override
	public Adresse read(Adresse adresse, EntityManager em) {
		Adresse adresseRetour = null;
		try {
			TypedQuery<Adresse> query = em.createQuery(
					"SELECT a FROM Adresse a  WHERE a.voie = ?1 AND a.num = ?2 AND a.ville = ?3 AND a.codePostal = ?4",
					Adresse.class);
			query.setParameter(1, JpaUtils.capitalize(adresse.getVoie()));
			query.setParameter(2, adresse.getNum());
			query.setParameter(3, adresse.getVille().toUpperCase());
			query.setParameter(4, adresse.getCodePostal());
			adresseRetour = query.getSingleResult();
		} catch (Exception e) {
		} finally {
			return adresseRetour;
		}

	}

	@SuppressWarnings("finally")
	@Override
	public Entreprise read(Entreprise entreprise, EntityManager em) {
		Entreprise entrepriseRetour = null;
		try {
			TypedQuery<Entreprise> query = em.createQuery("SELECT e FROM Entreprise e WHERE e.nom = ?1",
					Entreprise.class);
			query.setParameter(1, entreprise.getNom().toUpperCase());
			entrepriseRetour = query.getSingleResult();
		} catch (Exception e) {
		} finally {
			return entrepriseRetour;
		}

	}
}