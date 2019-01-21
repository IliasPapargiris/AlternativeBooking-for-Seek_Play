package Dao;

import Model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;


import Model.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Deadpool
 */
@Repository

public class UserDao { 
    @PersistenceContext 
    private EntityManager em;
    @Transactional
    public User login(String username,String check_pass){
       User found=em.find( User.class,username );
       String password= found.getPassword();
      if(password.equals(check_pass)){
       return found;
        }else{
          return null;
      }
     }

    /**
     *
     * @param username
     * @param password
     * @param firstname
     * @param lastname
     * @param password
     * @param profileimage
     * @return
     */
    @Transactional
    public User register(int user_id,String username,String password,String firstname,String lastname,String img){
        User newUser=new User();
        if(check_username(user_id)==null){
        newUser.setFirstname(username);
        newUser.setLastname(lastname);    
        newUser.setPassword(password);
        newUser.setImage(img);
        em.persist(newUser);
        return newUser;
        }else
        return null;       
    }
    
    @Transactional
    public User check_username(int user_id){
        User checking=em.find(User.class, user_id);
        return checking;
    }

    @Transactional 
    public List<String> get_available_players(String date,String hours){
        List<String> playas;
      Query q=em.createNativeQuery("SELECT USERNAME FROM seek_play.game_request INNER JOIN court_reservation ON court_reservation.CourtReservationID=game_request.match "
  + "INNER JOIN user on user.user_id=game_request.request_receiver where( (game_request.status='pending' or game_request.status='no') and (date='"+date+"' and hours='"+hours+"'))");
        playas=q.getResultList();
        return playas;
    }
}
