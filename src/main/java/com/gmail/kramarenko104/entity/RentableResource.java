package com.gmail.kramarenko104.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "rentable")
@NamedQueries({
        @NamedQuery(name = "listResourcesForRent", query = "select r from RentableResource r where r.leftAmount > 0"),
})
public class RentableResource extends Resource {

    private int totalAmount;

    public RentableResource() {
        super();
    }

    public void setTotalAmount(int amount) {
        this.totalAmount = amount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    @Override
    public void addResource(int addAmount) {
        super.addResource(addAmount);
        totalAmount += addAmount;
    }

    public void checkinResource(int rentBackAmount) {
        super.addResource(rentBackAmount);
    }

    public void removeResource(int amount) {
        super.removeResource(amount);
        totalAmount -= amount;
    }
}
