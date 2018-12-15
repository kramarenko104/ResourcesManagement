package com.gmail.kramarenko104.entity;

import javax.persistence.*;

@Entity
@Table (name = "resources")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "listAllAvailableResources", query = "select r from Resource r where r.leftAmount > 0")
public abstract class Resource {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;
    private String name;
    protected int leftAmount;

    public Resource(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(int leftAmount) {
        this.leftAmount = leftAmount;
    }

    public int takeResource(int takeAmount) {
        int tookAmount  = 0;
        if (leftAmount > 0 && takeAmount > 0) {
            if (leftAmount >= takeAmount) {
                leftAmount -= takeAmount;
                tookAmount = takeAmount;
            } else {
                tookAmount = leftAmount;
                leftAmount = 0;
            }
        }
        return tookAmount;
    }

    public void addResource(int addAmount) {
        leftAmount += addAmount;
    }

    public void removeResource(int amount){
        leftAmount -= amount;
    }

    @Override
    public String toString() {
        return "'" + getName() + "'";
    }
}
