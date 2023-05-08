package com.samar.voitures.entities;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Marque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMarque;
	
	@NotNull
	@Size (min = 3,max = 15)
	private String nomMarque;
	
	
	@Size (min = 4,max = 100)
	private String descriptionMarque;
	
	@JsonIgnore
	@OneToMany(mappedBy = "marque")
	private List<Voiture> Voitures;

	@Override
	public String toString() {
		return "Marque [idMarque=" + idMarque + ", nomMarque=" + nomMarque + ", descriptionMarque=" + descriptionMarque
				+ ", Voitures=" + Voitures + "]";
	}

	
}
