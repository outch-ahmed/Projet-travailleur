package fr.aston.poec.jakarta.model;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import lombok.*;
import lombok.experimental.FieldDefaults;

@MappedSuperclass
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class GlobalEntity {
	@JsonbTransient
	@Column
	@Version
	int version;
	
	@JsonbTransient
	@Column
	LocalDateTime createdAt;

	@PrePersist
	private void init() {
		createdAt = LocalDateTime.now();
	}

}
