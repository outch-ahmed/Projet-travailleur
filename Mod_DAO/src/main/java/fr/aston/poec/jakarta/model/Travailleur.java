package fr.aston.poec.jakarta.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.*;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
//@EqualsAndHashCode(of = {"nom", "prenom", "email"}, callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@NoArgsConstructor
@JsonbPropertyOrder({  "id", "nom", "prenom", "email", "ddn", "age", "adresse", "telephone", "siteweb", "entreprise" })
@XmlRootElement(name = "travailleur")
@XmlAccessorType(XmlAccessType.FIELD)
public class Travailleur extends GlobalEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@NonNull
	@Column(length = 20, name = "nom")
	String nom;

	@NonNull
	@Column(length = 20, name = "prenom")
	String prenom;

	@NonNull
	@Column(length = 30, name = "email")
	String email;

	@NonNull
	@JsonbDateFormat(locale = "fr_FR", value = "dd-MM-yyyy")
	@Column(name = "ddn")
	LocalDate ddn;

	@Transient
	int age;

	@ManyToOne
	Adresse adresse;

	@Column(name = "telephone", length = 30)
	String telephone;

	@Column(name = "siteweb", length = 30)
	String siteweb;

	@ManyToOne
	Entreprise entreprise;

	public Travailleur(String nom, String prenom, String email, LocalDate ddn) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.ddn = ddn;
	}

	@PrePersist
	private void beforePersistence() {
		setNom(nom.toUpperCase());
		setPrenom(JpaUtils.capitalize(prenom));
		setEmail(email.trim().toLowerCase());
	}

	@PostLoad
	private void postLoad() {
		age = JpaUtils.calculeAge(ddn);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Travailleur other = (Travailleur) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.toLowerCase().equals(other.email.toLowerCase()))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.toLowerCase().equals(other.nom.toLowerCase()))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.toLowerCase().equals(other.prenom.toLowerCase()))
			return false;
		return true;
	}
	

}
