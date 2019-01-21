/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.math.BigDecimal;

/**
 *
 * @author User
 */
public class PseudoStats {
    private int player;
    private String username;
     
     private String teamwork;
     private String athletism;
     private String technique;

    public int getPlayer() {
        return player;
    }

    public String getUsername() {
        return username;
    }

   
   
    public void setPlayer(int player) {
        this.player = player;
    }

    public void setUsername(String username) {
        this.username = username;
    }

   

    public void setAthletismStatsFromStats(BigDecimal athl) {
        String a=athl.toString();
        this.athletism = a;
    }

    public void setTechniqueFromStats(BigDecimal tech) {
        String t=tech.toString();
        this.technique = t;
    }
    
      public void setTeamworkFromStats(BigDecimal team) {
       String te=team.toString();
        this.teamwork = te;
    }

    public void setTeamworkFromUser(String teamwork) {
        this.teamwork = teamwork;
    }
      
    public void setAthletismFromUser(String athletism) {
        this.athletism = athletism;
    }

    public void setTechniqueFromUser(String technique) {
        this.technique = technique;
    }

    public String getTeamwork() {
        return teamwork;
    }

    public String getAthletism() {
        return athletism;
    }

    public String getTechnique() {
        return technique;
    }

}
