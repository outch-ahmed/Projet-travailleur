package fr.aston.poec.jakarta.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
//@EqualsAndHashCode(of = { "nom", "activite", "slogan" }, callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@NoArgsConstructor
@JsonbPropertyOrder({ "nom", "activite", "slogan" })
@XmlRootElement(name = "entreprise")
@XmlAccessorType(XmlAccessType.FIELD)
public class Entreprise extends GlobalEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonbTransient
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@NonNull
	@Column(length = 35)
	String nom;

	@NonNull
	String activite;

	@NonNull
	String slogan;

	public Entreprise(String nom, String activite, String slogan) {
		this.nom = nom;
		this.activite = activite;
		this.slogan = slogan;
	}

	@PrePersist
	public void beforePersistence() {
		setNom(JpaUtils.capitalize(nom));
		setActivite(JpaUtils.capitalize(activite));
		setSlogan(JpaUtils.capitalize(slogan));

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activite == null) ? 0 : activite.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((slogan == null) ? 0 : slogan.hashCode());
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
		Entreprise other = (Entreprise) obj;
		if (activite == null) {
			if (other.activite != null)
				return false;
		} else if (!activite.toLowerCase().equals(other.activite.toLowerCase()))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.toLowerCase().equals(other.nom.toLowerCase()))
			return false;
		if (slogan == null) {
			if (other.slogan != null)
				return false;
		} else if (!slogan.toLowerCase().equals(other.slogan.toLowerCase()))
			return false;
		return true;
	}
	

}
