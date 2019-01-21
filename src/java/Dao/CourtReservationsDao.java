/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Court;
import Model.CourtReservation;
import Model.Hours;
import Model.Stats;
import Model.User;
import java.io.Serializable;
import java.sql.Connection;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.Cache;
import org.springframework.stereotype.Repository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.StatelessSessionBuilder;
import org.hibernate.Transaction;
import org.hibernate.TypeHelper;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;



/**
 *
 * @author User
 */
@Repository
public class CourtReservationsDao {
    Session session; 
    @PersistenceContext 
    EntityManager em;
     @Transactional
public List<Integer> check_hours(int court_id,Date date){
 List<Integer> checker;
 Query q=em.createNativeQuery("SELECT hour FROM seek_play.court_reservation inner join hours on court_reservation.hours=hours.hour "
         + "where court_reservation.courtname='"+court_id+"' and court_reservation.date='"+date+"' and court_reservation.booker is null");
checker=q.getResultList();
return checker;
}

@Transactional
 public List<Hours> get_available_hours(String court,String date){
      List<Hours> hour;
  Query q=em.createNativeQuery("select * from hours where hours.hours_id not in (select hours from court_reservation where court_id='"+court+"' and date='"+date+"')",Hours.class);
  hour=q.getResultList();
  return  hour;  
  }
@Transactional
 public ArrayList<String> exclude_dates(String court){
  List<Date> booked;
  Query q=em.createNativeQuery("SELECT date FROM seek_play.unavailable_dates where court_id='"+court+"'");
  booked=q.getResultList();
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
  //String excluded= sdf.format(booked);
  ArrayList<String> Excluded=new ArrayList();
  for(Date date:booked){
   String akuro=sdf.format(date);
   Excluded.add(akuro);
   System.out.println(Excluded);
  }
  return  Excluded;  
  }
public void makeReservation(CourtReservation res){
    em.persist(res);    
}
public boolean checkRes(CourtReservation res){
   int court=res.getCourtId().getId();   
   Date resDate=res.getDate();
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date=sdf.format(resDate);
   int hour=res.getHours().getHoursId();
    Query q=em.createNativeQuery("SELECT * from court_reservation where ((court_reservation.court_id='"+court+"' and court_reservation.date='"+date+"') and court_reservation.hours='"+hour+"')",CourtReservation.class);
   CourtReservation checker= (CourtReservation) q.getSingleResult();
   if (checker.equals(res)){
   return true;
   }else
    return false;  
 }


//public boolean makeReservation(String court,String date,String hour) throws ParseException{
//    Court c=new Court();
//    int g = Integer.parseInt(court);
//    c=em.find(Court.class,g);
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    Date bookedDate=sdf.parse(date);
//    Hours h=new Hours();
//    int g1 = Integer.parseInt(hour);
//    h=em.find(Hours.class,g1);
//    CourtReservation k=new CourtReservation();
//    k.setCourtId(c);
//    k.setDate(bookedDate);
//    k.setHours(h);
//    if(this.checkRes(k)==false){
//    em.persist(k);
//    return true;
//    }  
//    return false;
//    
//}
@Transactional
public boolean makeRes(String court,String date,String hour){
     CourtReservation check = null ;
    try{
   Query q1=em.createNativeQuery("Select * from court_reservation where (court_reservation.court_id='"+court+"' and court_reservation.hours='"+hour+"' and (court_reservation.date='"+date+"'))",CourtReservation.class);
   check=(CourtReservation) q1.getSingleResult();
    } catch (NoResultException e){
}
    boolean a=(check==null);
    if(a==true){
    Query q=em.createNativeQuery("INSERT INTO seek_play.court_reservation (court_reservation.booker,court_reservation.court_id,court_reservation.date,court_reservation.hours)\n" +
    "VALUES('1','"+court+"','"+date+"','"+hour+"');",CourtReservation.class); 
    q.executeUpdate();
    em.flush();
    return true;
    }
    return false;
    //Select CourtReservationID from court_reservation where court_id='' and hours='' and  date='';
}
public boolean exists(String court,String date,String hour){
   CourtReservation check=new CourtReservation();
    try{
   Query q1=em.createNativeQuery("Select * from court_reservation where (court_reservation.court_id='"+court+"' and court_reservation.hours='"+hour+"' and (court_reservation.date='"+date+"'))",CourtReservation.class);
   check=(CourtReservation) q1.getSingleResult();
    } catch (NoResultException e){        
    }
    
    int flag= check.getCourtId().getId();
    String flag1=Integer.toString(flag);
    if(flag1.equals(court)){
        return true;
    }
    else return false;
}


public String courtname(String Court){
     int d = Integer.parseInt(Court);
     Court c=em.find(Court.class, d);
     String name=c.getName();
     return name;
}
public int  courtResID(String Court,String date,String hour){
   int k=0;
   Query q=em.createNativeQuery("Select CourtReservationID from court_reservation where court_id='"+Court+"' and hours='"+hour+"' and  date='"+date+"'");
   int id=(int) q.getSingleResult();
   //k=Integer.parseInt(id);
   return id;
}

@Transactional 
public boolean check_passedDate(String date){
    Date todayDate=new Date();
    Format conventer = new SimpleDateFormat("yyyy-MM-dd");
    String today1 = conventer.format(todayDate);
    if(this.check_Date_Format(date)==true){
     StringBuilder cb = new StringBuilder(today1);
     cb.deleteCharAt(4);
     cb.deleteCharAt(6);
     String today=cb.toString();
     String Syear=today.substring(0, 4);
     String Smonth=today.substring(4, 6);
     String Sday=today.substring(6);  
     int currentYear= Integer.parseInt(Syear);
     int currentMonth=Integer.parseInt(Smonth);
     int currentDay= Integer.parseInt(Sday);    
    StringBuilder sb = new StringBuilder(date);
    sb.deleteCharAt(4);
    sb.deleteCharAt(6);
    String d2=sb.toString();
    String Dyear=d2.substring(0, 4);
     String Dmonth=d2.substring(4, 6);
     String Dday=d2.substring(6);
      int Year= Integer.parseInt(Dyear);
     int Month=Integer.parseInt(Dmonth);
     int Day= Integer.parseInt(Dday);
     if(Year<currentYear){
        return false;
    }else if(Year==currentYear && Month<currentMonth){
        return false;
    }else if(Year==currentYear && Month==currentMonth && Day<currentDay){
        return false;
    }else{
        return true;
    }
    }else
        return false;
}

public boolean check_Month_Day(String date){
    StringBuilder sb = new StringBuilder(date);
    sb.deleteCharAt(4);
    sb.deleteCharAt(6);
    String d2=sb.toString();
    String Dyear=d2.substring(0, 4);
     String Dmonth=d2.substring(4, 6);
     String Dday=d2.substring(6);
      int Year= Integer.parseInt(Dyear);
     int Month=Integer.parseInt(Dmonth);
     int Day= Integer.parseInt(Dday);
    if(((Month==1 || Month==3) || ((Month==5) || Month==7) || (Month==8 || Month==10) || Month==12)&& (Day<1 || Day>31)){
    return false;
        }
    if(((Month==4 || Month==6)||(Month==9|| Month==11))&&(Day<1 || Day>30)){
        return false;
    }
    if(Month==2||(Day<1 ||Day>28)){
        return false;
    }
    return true;
}

public boolean check_Month(String date){
    StringBuilder sb = new StringBuilder(date);
    sb.deleteCharAt(4);
    sb.deleteCharAt(6);
    String date1=sb.toString();
    String month=date1.substring(4, 6);
      int Month=Integer.parseInt(month);
 if(Month<1 || Month>12){
     return false;
 }
 return true;
}

public boolean check_Date_Format(String date){
   String numbers=date.replaceAll("-", "");
   boolean isNumeric = numbers.chars().allMatch( Character::isDigit );
   char a=date.charAt(4);
   char b=date.charAt(7);
   String check1=Character.toString(a);
   String check2=Character.toString(b);

   if(date.length()!=10 && !check1.equals("-") && check2.equals(check2) && (isNumeric==true)){
    return false;    
    }else
    return true;
}
public boolean MaxDatePicker(String date){
     Date todayDate=new Date();
    Format conventer = new SimpleDateFormat("yyyy-MM-dd");
    String today = conventer.format(todayDate);
 StringBuilder cb = new StringBuilder(today);
     cb.deleteCharAt(4);
     cb.deleteCharAt(6);
     String today1=cb.toString();
     String Syear=today1.substring(0, 4);
     String Smonth=today1.substring(4, 6);
     String Sday=today1.substring(6);  
     int currentYear= Integer.parseInt(Syear);
     int currentMonth=Integer.parseInt(Smonth);
     int currentDay= Integer.parseInt(Sday);    
    StringBuilder sb = new StringBuilder(date);
    sb.deleteCharAt(4);
    sb.deleteCharAt(6);
    String d2=sb.toString();
    String Dyear=d2.substring(0, 4);
     String Dmonth=d2.substring(4, 6);
     String Dday=d2.substring(6);
      int Year= Integer.parseInt(Dyear);
     int Month=Integer.parseInt(Dmonth);
     int Day= Integer.parseInt(Dday);
     //tsekare to!!!
if(Month!=12 && Month>(currentMonth + 1)){
    return false;
    
}if((Month==12 && (currentMonth!=12)) && currentMonth>1){
    return false;
}
if((Month==1 && currentMonth==12)&&(currentDay<Day)){
    return false;
}
return true;
}

public boolean checkLeapYear(String date){
    ArrayList<String> leapYears=new ArrayList<String>();
    int start=2020;
    for(int i=0; i<=100; i++){
       int year=(start+4);
       String Leap=Integer.toString(year);
       leapYears.add(Leap);
}
    StringBuilder sb = new StringBuilder(date);
    sb.deleteCharAt(4);
    sb.deleteCharAt(6);
    String d2=sb.toString();
    String Dyear=d2.substring(0, 4);
    if(leapYears.contains(Dyear)){
        return true;
    }else
        return false;
}

public boolean check_LeapYear_Month_Day(String date){
 StringBuilder sb = new StringBuilder(date);
    sb.deleteCharAt(4);
    sb.deleteCharAt(6);
    String d2=sb.toString();
    String Dyear=d2.substring(0, 4);
     String Dmonth=d2.substring(4, 6);
     String Dday=d2.substring(6);
      int Year= Integer.parseInt(Dyear);
     int Month=Integer.parseInt(Dmonth);
     int Day= Integer.parseInt(Dday);

    if(((Month==1 || Month==3) || ((Month==5) || Month==7) || (Month==8 || Month==10) || Month==12)&& (Day<1 || Day>31)){
    return false;
        }
    if(((Month==4 || Month==6)||(Month==9|| Month==11))&&(Day<1 || Day>30)){
        return false;
    }
    if(Month==2||(Day<1 ||Day>29)){
        return false;
    }
    return true;
}

public boolean check_Booked_Date(String Date){
 ArrayList<String> booked=new ArrayList();
 ArrayList<Date> Date2String =new ArrayList();
 try{
 Query q=em.createNativeQuery("SELECT date FROM unavailable_dates");
 Date2String=(ArrayList<Date>) q.getResultList();
}catch (NoResultException e){        
 }
if(Date2String.isEmpty()==false){
 for(Date i:Date2String){
    Format conventer = new SimpleDateFormat("yyyy-MM-dd");
    String bookedDay = conventer.format(i);
    booked.add(bookedDay);
 } 
 if(booked.contains(Date)){
     return true;
 }
 else return false;
}
 return false;
}
}