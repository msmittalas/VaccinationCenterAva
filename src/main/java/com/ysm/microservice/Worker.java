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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
	
	
	
	
	   @Scheduled(initialDelay = 3000, fixedDelayString = "${fixedDelay}")
	   public void checkForAvailablity() {
	       
		    String toDay=getCurrentDate(LocalDateTime.now().toLocalDate());
		    Slots allSlots=new Slots();
		    allSlots.setCenters(new ArrayList<Center>());
		    if(!pincode.equals("-1")) {
		    	allSlots.getCenters().addAll(getSlotsforPinCode(toDay).getCenters());	
		    }
			if(!districtId.equals("-1")) {
				allSlots.getCenters().addAll(getSlotsForDistrictId(toDay).getCenters());	
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
		return template.getForEntity(URI.
				create("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id="+districtId+"&date="+toDay),
				Slots.class).getBody();
	}


	private Slots getSlotsforPinCode(String toDay) {
		return template.getForEntity(URI.
			create("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode="+pincode+"&date="+toDay),
			Slots.class).getBody();
	}
}
