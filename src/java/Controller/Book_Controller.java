/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.CourtDao;
import Dao.CourtReservationsDao;
import Dao.HoursDAO;
import Model.Court;
import Model.CourtReservation;
import Model.Hours;
import Model.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author User
 */
@Controller
public class Book_Controller {
    @Autowired
  private CourtDao courtdao;
    @RequestMapping(value="booking_creation.htm", method=RequestMethod.GET)
    public String list(ModelMap map, @ModelAttribute("reservation") CourtReservation reservation){
    User user=new User();
    Hours hours=new Hours();
    Date date=new Date();
    Court court=new Court();   
    
    reservation.setCourtId(court);
     reservation.setDate(date);
     reservation.setBooker(user);
     reservation.setHours(hours);
     List<Court> courtlist;
     courtlist=courtdao.Court_List();
     
     map.put("reservation",reservation);
     map.put("courtlist",courtlist);
     return "booking_creation";
    }
}
// @RequestMapping(value="book", method=RequestMethod.GET)
//public String make_reservation(ModelMap map, CourtReservation reservation,@RequestParam(value="date") String date,BindingResult result){
// 
//    Court court=reservation.getCourtId();
//    Hours hour=reservation.getHours();
//    CourtReservation second=new CourtReservation();
//    List<Court>courtlist=courtdao.Court_List();
//    map.put("courtlist", courtlist);
//    map.put("reservation", second);
//    return "booking_creation";    }
//}