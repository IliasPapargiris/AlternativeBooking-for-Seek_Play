/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public class Football_ReviewsDAO {
     @PersistenceContext 
    EntityManager em;
  @Transactional
  public int player_teamwork(int player){
     int teamwork;
     Query q= em.createNativeQuery("SELECT sum(teamwork)/count(reviewed) FROM seek_play.football_review where reviewed='"+player+"'  group by reviewed");
     teamwork=(int) q.getSingleResult();
     return teamwork;
  }
  
  @Transactional
  public int player_athletism(int player){
     int athletism;
     Query q= em.createNativeQuery("SELECT sum(athletism)/count(reviewed) FROM seek_play.football_review where reviewed='"+player+"'  group by reviewed");
     athletism=(int) q.getSingleResult();
     return athletism;
  }

   @Transactional
  public int player_technique (int player){
     int technique ;
     Query q= em.createNativeQuery("SELECT sum(technique )/count(reviewed) FROM seek_play.football_review where reviewed='"+player+"'  group by reviewed");
     technique =(int) q.getSingleResult();
     return technique ;
  }

}
