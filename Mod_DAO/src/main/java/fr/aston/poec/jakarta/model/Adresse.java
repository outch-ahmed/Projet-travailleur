package fr.aston.poec.jakarta.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
//@EqualsAndHashCode(of = { "voie", "num", "codePostal", "ville" }, callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@NoArgsConstructor
@JsonbPropertyOrder({"voie", "num", "ville", "codePostal", "geo"})
@XmlRootElement(name = "adresse")
@XmlAccessorType(XmlAccessType.FIELD)
public class Adresse extends GlobalEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonbTransient
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@NonNull
	@Column(length = 40)
	String voie;

	@NonNull
	@Column(length = 25)
	String num;

	@NonNull
	@Column(length = 25)
	String ville;

	@NonNull
	@Column(length = 15)
	String codePostal;

	@ManyToOne
	Geo geo;

	public Adresse(String voie, String num, String ville, String codePostal) {
		this.voie = voie;
		this.num = num;
		this.ville = ville;
		this.codePostal = codePostal;
	}
	
	

	@PrePersist
	public void beforePersistence() {
		setVoie(JpaUtils.capitalize(voie));
		setVille(ville.toUpperCase());
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codePostal == null) ? 0 : codePostal.hashCode());
		result = prime * result + ((num == null) ? 0 : num.hashCode());
		result = prime * result + ((ville == null) ? 0 : ville.hashCode());
		result = prime * result + ((voie == null) ? 0 : voie.hashCode());
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
		Adresse other = (Adresse) obj;
		if (codePostal == null) {
			if (other.codePostal != null)
				return false;
		} else if (!codePostal.equals(other.codePostal))
			return false;
		if (num == null) {
			if (other.num != null)
				return false;
		} else if (!num.equals(other.num))
			return false;
		if (ville == null) {
			if (other.ville != null)
				return false;
		} else if (!ville.toLowerCase().equals(other.ville.toLowerCase()))
			return false;
		if (voie == null) {
			if (other.voie != null)
				return false;
		} else if (!voie.toLowerCase().equals(other.voie.toLowerCase()))
			return false;
		return true;
	}

}
