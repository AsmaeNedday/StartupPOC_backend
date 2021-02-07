package fr.tse.fise3.poc.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import fr.tse.fise3.poc.domain.Time;
import fr.tse.fise3.poc.domain.User;
import fr.tse.fise3.poc.dto.TimeRequest;
import fr.tse.fise3.poc.exporter.UserReportExporter;
import fr.tse.fise3.poc.service.TimeService;
import fr.tse.fise3.poc.service.UserService;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600, methods = { RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH })
public class TimeController {
	
	@Autowired 
	private TimeService timeService;
	
	@Autowired 
	private UserService userService;
	
	@GetMapping("/times")
	public ResponseEntity<Collection<Time>> findAllTimes(){
		return new ResponseEntity<Collection<Time>>(this.timeService.findAllTimes(),HttpStatus.OK);
	}
	
	@PostMapping("/times")
	public ResponseEntity<Time> createTime(@RequestBody TimeRequest timeRequest) {
		return new ResponseEntity<Time>(this.timeService.createTime(timeRequest),HttpStatus.CREATED) ;
	}
	
	@GetMapping("/times/content/{userId}")
	public ResponseEntity<Collection<Time>> index(@PathVariable Long userId ) {
		return new ResponseEntity<Collection<Time>>(timeService.getTimeContent(userId),HttpStatus.OK);
	}

	
	@GetMapping("/times/{userId}/export/pdf")
	public void exportToPDF(HttpServletResponse response, @PathVariable Long userId ) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=times_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        List<Time> timesOfUser = timeService.findTimesOfUser(userId);
	        
	        User user = userService.findUser(userId);
	         
	        UserReportExporter exporter = new UserReportExporter(timesOfUser,user);
	        exporter.export(response);
	         
	    }
	
	

	@GetMapping("/times/{userId}")
	Collection<Time> findUserTimesForManager(@PathVariable Long userId) {
		return this.timeService.findTimesOfUser(userId) ;
	}
}