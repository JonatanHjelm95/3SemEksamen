/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 *
 * @author jonab
 */
@Entity

@NamedQueries({
    @NamedQuery(name = "Delivery.deleteAllRows", query = "DELETE from Delivery d")
    ,
    @NamedQuery(name = "Delivery.findAll", query = "SELECT d FROM Delivery d")})

public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shippingDate;
    private String fromLocation;
    private String Destination;

    //BiDirectional
    //@JsonIgnore(true)
    @ManyToOne(cascade = CascadeType.ALL)
    private Truck truck;

    //unidirectional
    //@JsonIgnore(true)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=(true))
    private List<Cargo> cargos;

    public Delivery(String shippingDate, String fromLocation, String Destination) {
        this.shippingDate = shippingDate;
        this.fromLocation = fromLocation;
        this.Destination = Destination;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public Delivery() {
    }

    public void addCargo(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public void addTruck(Truck truck) {
        this.truck = truck;
    }

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
        if (!(object instanceof Delivery)) {
            return false;
        }
        Delivery other = (Delivery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Delivery{" + "id=" + id + ", shippingDate=" + shippingDate + ", fromLocation=" + fromLocation + ", Destination=" + Destination + ", truck=" + truck + ", cargos=" + cargos + '}';
    }


}
