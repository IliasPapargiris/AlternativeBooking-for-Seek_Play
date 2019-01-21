/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.CourtDao;
import Dao.CourtReservationsDao;
import Dao.GameRequestDao;
import Dao.HoursDAO;
import Model.Court;
import Model.Hours;
import Model.PseudoStats;
import Model.Stats;
import Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("bookcreation")
public class BookEventController {
     
  @Autowired
  private CourtReservationsDao courtreservationsdao;
 @Autowired
 private GameRequestDao maGames;
  
    /**
     *
     * @param name
     * @return
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @RequestMapping(value="court", method=RequestMethod.GET, headers="Accept=*/*",produces="application/json")
      public  @ResponseBody String list(@RequestParam(value="court" )String text ) throws JsonProcessingException{
          ArrayList<String> booked_dates=courtreservationsdao.exclude_dates(text);
         ObjectMapper mapper= new ObjectMapper();
         System.out.println(mapper.writeValueAsString(booked_dates));
         return mapper.writeValueAsString(booked_dates);
      }
 @RequestMapping(value="hour", method=RequestMethod.GET, headers="Accept=*/*",produces="application/json")
      public  @ResponseBody String hours(@RequestParam(value="court" )String text,@RequestParam(value="date") String text1 ) throws JsonProcessingException{
          List<Hours> booked_dates=courtreservationsdao.get_available_hours(text, text1);
         ObjectMapper mapper= new ObjectMapper();
         System.out.println(mapper.writeValueAsString(booked_dates));
         return mapper.writeValueAsString(booked_dates);
      }

@RequestMapping(value="booking", method=RequestMethod.GET, headers="Accept=*/*",produces="application/json")
    public  @ResponseBody String bookRes(@RequestParam(value="court" )String court,@RequestParam(value="date") String date,@RequestParam(value="hours") String hour ) throws JsonProcessingException, ParseException{
           String failed="You already made this !";
           ObjectMapper mapper= new ObjectMapper();
           if(courtreservationsdao.check_Date_Format(date)==false){
               String fail1="Invalid date format";
             System.out.println(mapper.writeValueAsString(fail1));
             return mapper.writeValueAsString(fail1);
           }
           boolean flag=courtreservationsdao.check_Month(date);
           if(flag==false){
               String fail2="Something went wrong!Invalid month on date";
             System.out.println(mapper.writeValueAsString(fail2));
             return mapper.writeValueAsString(fail2); 
           }
           boolean flag1=courtreservationsdao.checkLeapYear(date);
            if(flag1==true){
               String fail4="Something went wrong!Invalid day on date";
              boolean flag12=courtreservationsdao.check_LeapYear_Month_Day(date);
              if(flag12==false)
             System.out.println(mapper.writeValueAsString(fail4));
             return mapper.writeValueAsString(fail4); 
                    }
           boolean flag2=courtreservationsdao.check_Month_Day(date);
            if(flag2==false){
               String fail3="Something went wrong!Invalid day on date";
             System.out.println(mapper.writeValueAsString(fail3));
             return mapper.writeValueAsString(fail3); 
           }
           boolean flag3=courtreservationsdao.check_passedDate(date);
            if(flag3==false){
               String fail4="Something went wrong!Invalid date,attemped to booked passed day";
             System.out.println(mapper.writeValueAsString(fail4));
             return mapper.writeValueAsString(fail4); 
           } 
            boolean flag4=courtreservationsdao.MaxDatePicker(date);
            if(flag4==false){
               String fail4="Something went wrong!Can not book a date a month over";
             System.out.println(mapper.writeValueAsString(fail4));
             return mapper.writeValueAsString(fail4); 
           }
           
          boolean flag5=courtreservationsdao.check_Booked_Date(date);
          if(flag5==true){
               String fail5="Something went wrong!This date is fully booked!";
             System.out.println(mapper.writeValueAsString(fail5));
             return mapper.writeValueAsString(fail5); 
          }
                  
          boolean flag6=courtreservationsdao.makeRes(court, date, hour);
          if(flag6==true){
          String hour2=hour.substring(0, 1);
          String booked="Game booked on "+date+" at "+hour2+" o'clock at court "+court;
          //c.setName(booked);
         System.out.println(mapper.writeValueAsString(booked));
         return mapper.writeValueAsString(booked);
      }else
         System.out.println(mapper.writeValueAsString(failed));
         return mapper.writeValueAsString(failed);
 }

@RequestMapping(value="request", method=RequestMethod.GET, headers="Accept=*/*",produces="application/json")
    public  @ResponseBody String sendReq(@RequestParam(value="date") String date,@RequestParam(value="hours")String hour ) throws JsonProcessingException, ParseException{
           List<Stats> players;
          players=maGames.availablePlayers(date, hour);
          ObjectMapper mapper= new ObjectMapper();
          return mapper.writeValueAsString(players);
        // System.out.println(mapper.writeValueAsString(players));
         //return players;
      
}
//
@RequestMapping(value="invite", method=RequestMethod.GET, headers="Accept=*/*",produces="application/json")
    public  @ResponseBody String invite(@RequestParam(value="court" )String court,@RequestParam(value="date") String date,@RequestParam(value="hours") String hour,@RequestParam(value="players") String receiver ) throws JsonProcessingException, ParseException{
           String failed="You already send this player an invitation !";
          int match=courtreservationsdao.courtResID(court, date, hour);
          String match1=Integer.toString(match);
          ObjectMapper mapper= new ObjectMapper();
          if(match==0){
          boolean a=maGames.sendReq(match1,receiver);
          
          String hour2=hour.substring(0, 1);
          String booked="Game booked on "+date+" at "+hour2+" o'clock at court "+court;
         System.out.println(mapper.writeValueAsString(booked));
         return mapper.writeValueAsString(booked);
      }
         System.out.println(mapper.writeValueAsString(failed));
         return mapper.writeValueAsString(failed);
}


@RequestMapping(value="request1", method=RequestMethod.GET, headers="Accept=*/*",produces="application/json")
    public  @ResponseBody String getPlayers(@RequestParam("game") String match ) throws JsonProcessingException, ParseException{
           List<Stats> players;
           String hour=maGames.hour4id(match);
           String date=maGames.Date4id(match);
          players=maGames.availablePlayers(date, hour);
          List<User> noStatsPlayers=maGames.availablePlayerswithNoGrades(date, hour);
          ArrayList<PseudoStats> forRequest =new ArrayList();
          for (Stats i : players) {
         PseudoStats a= new PseudoStats();
         a.setPlayer(i.getPlayer());
         a.setUsername(i.getUsername());
         a.setAthletismStatsFromStats(i.getAthletism());
         a.setTeamworkFromStats(i.getTeamwork());
         a.setTechniqueFromStats(i.getTechnique());
         forRequest.add(a);
        }
          for(User i: noStatsPlayers){
          PseudoStats a= new PseudoStats(); 
          a.setPlayer(i.getUserId());
          a.setUsername(i.getUsername());
          a.setAthletismFromUser("-");
          a.setTeamworkFromUser("-");
          a.setTechniqueFromUser("-");
          forRequest.add(a);
          }
          forRequest.size();
          ObjectMapper mapper= new ObjectMapper();
          System.out.println(mapper.writeValueAsString(forRequest));
          return mapper.writeValueAsString(forRequest);           
    }

    /**
     *
     * @param match
     * @param player
     * @return
     * @throws JsonProcessingException
     * @throws ParseException
     */
    @RequestMapping(value="SendReq", method=RequestMethod.GET, headers="Accept=*/*",produces="application/json")
    public  @ResponseBody String sendRequest(@RequestParam("game") String match,@RequestParam("players") String player ) throws JsonProcessingException, ParseException{
           List<Stats> players;
           String hour1=maGames.hour4id(match);
           String hour=hour1.substring(0, 2);
           String date=maGames.Date4id(match);
           String court=maGames.court4id(match);
           String receiver=maGames.Name4Receiver(player);
            ObjectMapper mapper= new ObjectMapper();
          boolean a=maGames.exists(match, player);
          if(a==false){
           boolean b=maGames.makeRequest(match, player);
          System.out.println(b);          
          String answer="You send a request for game taking place at "+court+" at "+date+" game starting at "+hour+"o'clock to "+ receiver;
          System.out.println(mapper.writeValueAsString(answer));
          return mapper.writeValueAsString(answer);
          }
          else{
          String answer1="You already send a request to "+receiver+" for this match";
          System.out.println(mapper.writeValueAsString(answer1));
          return mapper.writeValueAsString(answer1);
          }
      }
}