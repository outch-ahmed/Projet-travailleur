package fr.aston.poec.jakarta.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = { "lat", "lng" }, callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@NoArgsConstructor
@JsonbPropertyOrder({"lat", "lng"})
@XmlRootElement(name = "geo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Geo extends GlobalEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonbTransient
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@NonNull
	@Column(length = 25)
	String lat;

	@NonNull
	@Column(length = 25)
	String lng;

	public Geo(String lat, String lng) {
		this.lat = lat;
		this.lng = lng;
	}
}
