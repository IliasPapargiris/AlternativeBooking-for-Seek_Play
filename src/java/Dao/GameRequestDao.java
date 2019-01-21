/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

/**
 *
 * @author User
 */

import Model.Court;
import Model.CourtReservation;
import Model.GameRequest;
import Model.Hours;
import Model.ShowMatches;
import Model.Stats;
import Model.User;
import java.io.Serializable;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import static org.hibernate.jpa.criteria.ValueHandlerFactory.isNumeric;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

@Repository
public class GameRequestDao {
    
 @PersistenceContext 
 EntityManager em;
 @Transactional
 public List<User> users_for_review(String court,String date,String hour){
     List<User> provata;
     Query q=em.createNativeQuery("SELECT * from 	Stats where Player in\n" +
"(select game_request.request_receiver from game_request inner join court_reservation on CourtReservationID=game_request.match \n" +
"where court_reservation.date!='"+date+"' and !court_reservation.hours!='"+court+"')",User.class);
 provata=q.getResultList();
 return provata;
 }
  
 @Transactional
public boolean sendReq(String match,String receiver){
     GameRequest check = null ;
   
    try{
   Query q1=em.createNativeQuery("Select * from game_request where game_request.match=''  and game_request.request_receiver=''",GameRequest.class);
   check=(GameRequest) q1.getSingleResult();
    } catch (NoResultException e){
}
    boolean a=(check==null);
    if(a==true){
    Query q=em.createNativeQuery("",GameRequest.class); 
    q.executeUpdate();
    em.flush();
    return true;
    }
    return false;
    
}
@Transactional
public List<ShowMatches> myMatches(String booker){
    Date todayDate= new Date();
     Format conventer = new SimpleDateFormat("yyyy-MM-dd");
    String today = conventer.format(todayDate);
   String fuck="SELECT * FROM seek_play.showMatches where date >="+today+" and booker='"+booker+"'";
   Query q=em.createNativeQuery(fuck,ShowMatches.class);
   List<ShowMatches> mygames=q.getResultList();
   return mygames;
}
//public boolean hasMatches(String booker){
//   CourtReservation check=null;
//    try{
//   Query q1=em.createNativeQuery("SELECT * FROM court_reservation where court_reservation.booker='"+booker+"'",CourtReservation.class);
//   check=(CourtReservation) q1.getSingleResult();
//    } catch (NoResultException e){
//    }     
//    if(check==null){
//        return false;
//    }    
//  return true;
//}

@Transactional
public String hour4id(String id){
    String g= "SELECT hours FROM seek_play.court_reservation where court_reservation.CourtReservationID='"+id+"'";
    System.err.println(g);
    Query q=em.createNativeQuery(g);
    int hour1=(int) q.getSingleResult();
    String hour2=Integer.toString(hour1);
     Query q1=em.createNativeQuery("SELECT hour from hours where hours_id='"+hour2+"'");
     String hour=(String)q1.getSingleResult();
    return hour;
}

@Transactional
public String Date4id(String id){
    Query q=em.createNativeQuery("SELECT date FROM seek_play.court_reservation where court_reservation.CourtReservationID='"+id+"'");
    Date date1=(Date) q.getSingleResult();
    Format conventer = new SimpleDateFormat("yyyy-MM-dd");
    String date = conventer.format(date1);
    return date;
}
@Transactional
public String court4id(String id){
    Query q=em.createNativeQuery("SELECT court_id FROM seek_play.court_reservation where court_reservation.CourtReservationID='"+id+"'");
    int court=(int) q.getSingleResult();
    String court1=Integer.toString(court);
    Query q1=em.createNativeQuery("SELECT name from court where id='"+court1+"'");
    String courtname=(String)q1.getSingleResult();
    return courtname;
}
 
@Transactional
public String Name4Receiver(String id){
    Query q=em.createNativeQuery("SELECT username from user where user_id='"+id+"'");
    String court=(String) q.getSingleResult();
    return court;
}

@Transactional
public List<Stats> availablePlayers(String date,String hour){
    Query q=em.createNativeQuery("SELECT * FROM Stats where player not in (select game_request.request_receiver from game_request  where\n" +
"game_request.match \n" +
" in(Select court_reservation.CourtReservationID from court_reservation where court_reservation.date='2018-12-16' and court_reservation.hours='7' and booker='2')) \n" +
" and player not in (Select booker from court_reservation where  court_reservation.date='2018-12-16' and court_reservation.hours='7' )  \n" +
" and player not in(select game_request.request_receiver from game_request where game_request.status='yes' and\n" +
"game_request.match in(Select court_reservation.CourtReservationID from court_reservation where !court_reservation.date='"+date+"' and !court_reservation.hours='"+hour+"'))", Stats.class);
    List<Stats> players=q.getResultList();
    return players;
}
@Transactional 
public List<User> availablePlayerswithNoGrades(String date,String hour){
   Query q=em.createNativeQuery("SELECT * FROM user where user_id not in (select game_request.request_receiver from game_request  where\n" +
"game_request.match \n" +
" in(Select court_reservation.CourtReservationID from court_reservation where court_reservation.date='2018-12-16' and court_reservation.hours='7' and booker='2')) \n" +
" and user_id not in (Select booker from court_reservation where  court_reservation.date='2018-12-16' and court_reservation.hours='7' )  \n" +
" and user_id not in(select game_request.request_receiver from game_request where game_request.status='yes' and\n" +
"game_request.match in(Select court_reservation.CourtReservationID from court_reservation where !court_reservation.date='"+date+"' and !court_reservation.hours='"+hour+"'))", User.class);
   List<User> list=(List<User>)q.getResultList();
   return list;
}

public boolean exists(String match,String receiver){
   GameRequest check=null;
    try{
   Query q1=em.createNativeQuery("SELECT * FROM seek_play.game_request where game_request.match='"+match+"' and request_receiver='"+receiver+"'",GameRequest.class);
   check=(GameRequest) q1.getSingleResult();
    } catch (NoResultException e){        
    }
      
    if(check==null){
        return false;
    }
    else return true;
}

@Transactional
public boolean makeRequest(String match,String receiver){
    
    if(exists(match,receiver)==false){
    Query q=em.createNativeQuery("INSERT INTO seek_play.game_request (game_request.match,game_request.request_id,game_request.request_receiver) \n" +
"VALUES('"+match+"','11','"+receiver+"')",GameRequest.class); 
    q.executeUpdate();
    em.flush();
    return true;
    }
    return false;
    //Select CourtReservationID from court_reservation where court_id='' and hours='' and  date='';
}
@Transactional 
public boolean check_passedDate(String date){
    Date todayDate=new Date();
    Format conventer = new SimpleDateFormat("yyyy-MM-dd");
    String today = conventer.format(todayDate);
    if(this.check_Date_Format(date)==true){
     today.replaceAll("-", "");
     String Syear=today.substring(0, 4);
     String Smonth=today.substring(4, 6);
     String Sday=today.substring(5);
     String Dyear=date.substring(0, 4);
     String Dmonth=date.substring(4, 6);
     String Dday=date.substring(5);
     int currentYear= Integer.parseInt(Syear);
     int currentMonth=Integer.parseInt(Smonth);
     int currentDay= Integer.parseInt(Sday);
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
    date.replaceAll("-", "");
    String Dyear=date.substring(0, 4);
     String Dmonth=date.substring(4, 6);
     String Dday=date.substring(5);
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
    date.replaceAll("-", "");
    String month=date.substring(4, 6);
      int Month=Integer.parseInt(month);
 if(Month<1 || Month>12){
     return false;
 }
 return true;
}

public boolean check_Date_Format(String date){
   String numbers=date.replaceAll("-", "");
   boolean isNumeric = numbers.chars().allMatch( Character::isDigit );
   if(date.length()!=10 && !date.contains("-") && (isNumeric==true)){
    return false;    
    }else
    return true;
}
public boolean MaxDatePicker(String date){
     Date todayDate=new Date();
    Format conventer = new SimpleDateFormat("yyyy-MM-dd");
    String today = conventer.format(todayDate);
    date.replaceAll("-", "");
    String Dyear=date.substring(0, 4);
     String Dmonth=date.substring(4, 6);
     String Dday=date.substring(5);
      int Year= Integer.parseInt(Dyear);
     int Month=Integer.parseInt(Dmonth);
     int Day= Integer.parseInt(Dday);
     String Syear=today.substring(0, 4);
     String Smonth=today.substring(4, 6);
     String Sday=today.substring(5);
     int currentYear= Integer.parseInt(Syear);
     int currentMonth=Integer.parseInt(Smonth);
     int currentDay= Integer.parseInt(Sday);
if(Month>(currentMonth + 1)){
    return false;
    
}if(Month==12 && currentMonth>1){
    return false;
}
return true;
}

}
