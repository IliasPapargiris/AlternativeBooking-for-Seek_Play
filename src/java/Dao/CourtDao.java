/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Court;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Deadpool
 */
@Repository
public class CourtDao {
    @PersistenceContext 
    private EntityManager em;
    @Transactional
public List<Court> Court_List(){
     List<Court> k;
    Query q= em.createNativeQuery("SELECT * FROM court",Court.class);
    k=q.getResultList();
    return k;
}


}