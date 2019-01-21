/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "showMatches")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShowMatches.findAll", query = "SELECT s FROM ShowMatches s")
    , @NamedQuery(name = "ShowMatches.findByCourtReservationID", query = "SELECT s FROM ShowMatches s WHERE s.courtReservationID = :courtReservationID")
    , @NamedQuery(name = "ShowMatches.findByCourtId", query = "SELECT s FROM ShowMatches s WHERE s.courtId = :courtId")
    , @NamedQuery(name = "ShowMatches.findByDate", query = "SELECT s FROM ShowMatches s WHERE s.date = :date")
    , @NamedQuery(name = "ShowMatches.findByBooker", query = "SELECT s FROM ShowMatches s WHERE s.booker = :booker")
    , @NamedQuery(name = "ShowMatches.findByName", query = "SELECT s FROM ShowMatches s WHERE s.name = :name")
    , @NamedQuery(name = "ShowMatches.findByAddress", query = "SELECT s FROM ShowMatches s WHERE s.address = :address")
    , @NamedQuery(name = "ShowMatches.findByHoursId", query = "SELECT s FROM ShowMatches s WHERE s.hoursId = :hoursId")
    , @NamedQuery(name = "ShowMatches.findByHour", query = "SELECT s FROM ShowMatches s WHERE s.hour = :hour")})
public class ShowMatches implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Id
    @GeneratedValue
    @Column(name = "CourtReservationID")
    private int courtReservationID;
    @Basic(optional = false)
    @Column(name = "court_id")
    private int courtId;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "booker")
    private int booker;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "hours_id")
    private int hoursId;
    @Column(name = "hour")
    private String hour;

    public ShowMatches() {
    }

    public int getCourtReservationID() {
        return courtReservationID;
    }

    public void setCourtReservationID(int courtReservationID) {
        this.courtReservationID = courtReservationID;
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBooker() {
        return booker;
    }

    public void setBooker(int booker) {
        this.booker = booker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getHoursId() {
        return hoursId;
    }

    public void setHoursId(int hoursId) {
        this.hoursId = hoursId;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
    
}
