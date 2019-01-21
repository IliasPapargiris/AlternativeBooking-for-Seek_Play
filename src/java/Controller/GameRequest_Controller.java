/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.GameRequestDao;
import Model.CourtReservation;
import Model.ShowMatches;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author User
 */
@Controller
public class GameRequest_Controller {
    @Autowired
    private GameRequestDao myRequests;
    @RequestMapping(value="my_reservations.htm", method=RequestMethod.GET)
    public String myMatches(ModelMap map){
        int k=1;
        String booker=Integer.toString(k);
        List<ShowMatches> myGames=myRequests.myMatches(booker);
     map.put("matchlist",myGames);
     return "my_reservations";     
    }
}