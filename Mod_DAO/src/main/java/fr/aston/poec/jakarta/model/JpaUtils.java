package fr.aston.poec.jakarta.model;

import java.time.LocalDate;
import java.time.Period;

public class JpaUtils {

	public static String capitalize(String mot) {
		return mot.toUpperCase().substring(0, 1) + mot.toLowerCase().substring(1);
	}

	public static int calculeAge(LocalDate ddn) {
		LocalDate now = LocalDate.now();
		return Period.between(ddn, now).getYears();
	}
}