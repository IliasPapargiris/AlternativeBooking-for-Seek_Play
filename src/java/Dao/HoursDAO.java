/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Court;
import Model.Hours;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public class HoursDAO {
     @PersistenceContext 
    EntityManager em;
  public int get_hourID(int hours){
      int hourID;
  Query q=em.createNativeQuery("SELECT hours_id FROM seek_play.hours where hour='"+hours+"'");
  hourID=(Integer)q.getSingleResult();
  return hourID;     
  }

  public List<Hours> get_hours(){
  List<Hours> hour;
  Query q=em.createNativeQuery("SELECT * FROM seek_play.hours");
  hour=q.getResultList();
  return hour;     
  }
 

}

