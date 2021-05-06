package com.ysm.microservice;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class Worker {

	@Autowired
	RestTemplate template;
	
	@Autowired
	SmsService sender;
	
	@Autowired
	EmailService emailService;
	
	@Value("${districtid}")
	private String districtId;
	
	@Value("${pincode}")
	private String pincode;
	
	
	@Value("${smsNotification}")
	private String smsNotification;
	
	@Value("${emailNotification}")
	private String emailNotification;
	
	@Value("${cowin.url}")
	private String apiUrl;
	
	
	
	   @Scheduled(initialDelay = 3000, fixedDelayString = "${fixedDelay}")
	   public void checkForAvailablity() {
	       try{
		    String toDay=getCurrentDate(LocalDateTime.now().toLocalDate());
		    Slots allSlots=new Slots();
		    allSlots.setCenters(new ArrayList<Center>());
		    if(!pincode.equals("-1")) {
		    	Slots temp=getSlotsforPinCode(toDay);
		    	if(temp!=null && temp.getCenters()!=null)
		    		allSlots.getCenters().addAll(temp.getCenters());	
		    }
			if(!districtId.equals("-1")) {
				Slots temp=getSlotsForDistrictId(toDay);
				if(temp!=null && temp.getCenters()!=null)
					allSlots.getCenters().addAll(temp.getCenters());	
		    }

		
		Map<Integer, List<Session>> availableCenters = new HashMap<>();
		Map<Integer,Center> centers= new HashMap<>();
		
		findSlots(allSlots, availableCenters, centers);
		if(availableCenters.size()>1)
		{	
			String msg=displayResults(availableCenters, centers);
			
			sendNotification(msg);
		}
		else
		{
			System.out.println("Not Available Yet Trying one more time....");
		}
	       }catch (Exception exception) {
	    	   
	    	   //TODO remove it 
	    	  exception.printStackTrace();
	    	   
		}
	       finally {
	    	   System.out.println("Task Completed...");
	       }
	   
	}


	private void sendNotification(String msg) {
		if(smsNotification.equals("true"))
		System.out.println("SMS STATUS-->"+sender.send("Slots Available check quickly"));
		if(emailNotification.equals("true"))
		System.out.println("Email Status--->"+emailService.send(msg));	
		
	}


	private String displayResults(Map<Integer, List<Session>> availableCenters, Map<Integer, Center> centers) {
		StringBuilder builder= new StringBuilder();
		for(Integer center : availableCenters.keySet())
		{
			Center current=centers.get(center);
			builder.append("Name:"+current.getName()+"\n");
			builder.append("Address:"+current.getAddress()+"\n");
			builder.append("======\n");
			
			
			
		}
		System.out.println(builder);
		return builder.toString();
	}


	private void findSlots(Slots allSlots, Map<Integer, List<Session>> availableCenters, Map<Integer, Center> centers) {
		for(Center b: allSlots.getCenters() )
		 {
			centers.put(b.getCenterId(), b);
			List<Session> centerSlots= b.getSessions();
			List<Session> availableSession=centerSlots.stream().filter(s->s.getAvailableCapacity()>0).collect(Collectors.toList());
			
			if(availableSession.size()>0)
			{
				List<Session> s=availableCenters.get(b.getCenterId());
				if(s==null)
				{
					s=new ArrayList<Session>();
				}
				s.addAll(availableSession);
				availableCenters.put(b.getCenterId(), s);
			}
			
		}
	}


	private String getCurrentDate(LocalDate date1) {
		return (date1.getDayOfMonth()>9?date1.getDayOfMonth():"0"+date1.getDayOfMonth()) 
				+"-"+(date1.getMonthValue()>9?date1.getMonthValue():"0"+date1.getMonthValue())+"-"+date1.getYear();
	}


	private Slots getSlotsForDistrictId(String toDay) {
		HttpHeaders headers= new HttpHeaders();
		headers.set("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.51");
		final HttpEntity<String> entity = new HttpEntity<String>(headers);
		return  template.exchange(getURL("calendarByDistrict","district_id",districtId,"date",toDay), HttpMethod.GET, entity, Slots.class)        
    	.getBody();
	}



	private Slots getSlotsforPinCode(String toDay) {
		
		HttpHeaders headers= new HttpHeaders();
		headers.set("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.51");
		final HttpEntity<String> entity = new HttpEntity<String>(headers);
        return template.exchange(getURL("calendarByPin", "pincode",pincode,"date",toDay),HttpMethod.GET, entity, Slots.class)
			.getBody();
	}
	


	private URI getURL(String searchBy,String...params) {
		Map<String,String> urlParams=new HashMap<>();
		urlParams.put("searchBy", searchBy);
		
		UriComponentsBuilder builder= UriComponentsBuilder.fromUriString(apiUrl)
		        .queryParam(params[0], params[1])
		        .queryParam(params[2], params[3]);
		return builder.buildAndExpand(urlParams).toUri();
	}
	
	
}
