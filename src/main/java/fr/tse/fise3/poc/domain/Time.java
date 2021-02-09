/**
 * 
 */
package fr.tse.fise3.poc.domain;

import java.time.LocalDateTime;
import java.time.Month;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 
 *
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Time {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime dateStart;
	
	private LocalDateTime dateEnd;
	
	private String dateOfProject;
	// many to one vers users
	@ManyToOne
	private User user;
	
	// many to one vers projet
	@ManyToOne
	private Project project;

}
