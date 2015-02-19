/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author jmatar
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Office.findAll", query = "SELECT o FROM Office o"),
    @NamedQuery(name = "Office.findById", query = "SELECT o FROM Office o WHERE o.id = :id"),
    @NamedQuery(name = "Office.findByName", query = "SELECT o FROM Office o WHERE o.name = :name")})
public class Office implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String name;
    @JoinColumn(name = "collegeID", referencedColumnName = "id")
    @ManyToOne
    private College collegeID;
    @JoinColumn(name = "schoolID", referencedColumnName = "id")
    @ManyToOne
    private School schoolID;
    @OneToMany(mappedBy = "officeID")
    private List<Device> deviceList = new ArrayList<>();

    public Office() {
    }

    public Office(Integer id) {
        this.id = id;
    }

    public Office(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public College getCollegeID() {
        return collegeID;
    }

    public void setCollegeID(College collegeID) {
        this.collegeID = collegeID;
    }

    public School getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(School schoolID) {
        this.schoolID = schoolID;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Office)) {
            return false;
        }
        Office other = (Office) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Office[ id=" + id + " ]";
    }
    
}
