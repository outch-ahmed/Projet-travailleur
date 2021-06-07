package fr.aston.poec.jakarta.resources;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import fr.aston.poec.jakarta.dao.impls.TravailleurDao;
import fr.aston.poec.jakarta.idao.ITravailleur;
import fr.aston.poec.jakarta.model.Adresse;
import fr.aston.poec.jakarta.model.Entreprise;
import fr.aston.poec.jakarta.model.Geo;
import fr.aston.poec.jakarta.model.Travailleur;

@Path("rest")
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
@Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public class TravailleurResource {
	
	@PersistenceContext(unitName = "PERSIST_PU")
	private EntityManager em;
	private ITravailleur dao = new TravailleurDao();
	
	@GET
	@Path("alltravailleurs")
	public List<Travailleur> allTravailleurs() {
		return dao.allTravailleurs(em);
	}

	@GET
	@Path("allgeos")
	public List<Geo> allGeos() {
		return dao.allGeos(em);
	}

	@GET
	@Path("alladresses")
	public List<Adresse> allAdresses() {
		return dao.allAdresses(em);
	}

	@GET
	@Path("allentreprises")
	public List<Entreprise> allEntreprises() {
		return dao.allEntreprises(em);
	}

	@POST
	public void create(Travailleur travailleur) {
		dao.create(travailleur, em);
	}

	@PUT
	public void update(Travailleur travailleur) {
		dao.update(travailleur, em);
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") Integer id) {
		dao.delete(id, em);
	}

	@GET
	@Path("geo")
	public Geo read(Geo geo) {
		return dao.read(geo, em);
	}

	@GET
	@Path("adresse")
	public Adresse read(Adresse adresse) {
		return dao.read(adresse, em);
	}

	@GET
	@Path("entreprise")
	public Entreprise read(Entreprise entreprise) {
		return dao.read(entreprise, em);
	}
}
