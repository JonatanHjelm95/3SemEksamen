/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author jonab
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Truck.deleteAllRows", query = "DELETE from Truck t")
    ,
    @NamedQuery(name = "Truck.findAll", query = "SELECT t FROM Truck t")})

public class Truck implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int capacity;

//    //Bidirectional
//    @OneToOne
//    private Driver driver;
//    
    //unidirectional
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=(true))
    private List<Delivery> deliveries;

    public Truck(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public Truck() {
    }

    public void addDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

//    public void addDriver(Driver driver) {
//        this.driver = driver;
//    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Truck)) {
            return false;
        }
        Truck other = (Truck) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Truck{" + "id=" + id + ", name=" + name + ", capacity=" + capacity;
    }

}
