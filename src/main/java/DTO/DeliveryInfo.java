/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Cargo;
import entities.Delivery;
import entities.Driver;
import entities.Truck;

/**
 *
 * @author jonab
 */
public class DeliveryInfo {
    
    private long driver_id, cargo_id;
    private String driver_name, truck_name, cargo_name, shippingDate, fromLocation, toLocation;
    private double cargo_weight;
    private int cargo_units;
    
    public DeliveryInfo(Cargo cargo, Delivery delivery, Driver driver, Truck truck){
        this.driver_id = driver.getId();
        this.cargo_id = cargo.getId();
        this.driver_name = driver.getName();
        this.truck_name = truck.getName();
        this.cargo_name = cargo.getName();
        this.shippingDate = delivery.getShippingDate();
        this.fromLocation = delivery.getFromLocation();
        this.toLocation = delivery.getDestination();
        this.cargo_weight = cargo.getWeight();
        this.cargo_units = cargo.getUnits();
    }
}
