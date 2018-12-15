package com.gmail.kramarenko104.entity;

import javax.persistence.*;

@Entity
@Table (name = "consumable")
@NamedQueries({
        @NamedQuery(name = "listResourcesForConsumption", query = "select r from ConsumableResource r where r.leftAmount > 0"),
        @NamedQuery(name = "listConsumedResources", query = "select r from ConsumableResource r where r.consumedAmount > 0")
})
public class ConsumableResource extends Resource {

    private int consumedAmount;

    public ConsumableResource(){
        super();
    }

    @Override
    public int takeResource(int takeAmount) {
        int tookAmount = super.takeResource(takeAmount);
        consumedAmount += tookAmount;
        return tookAmount;
    }

    public int getConsumed() {
        return consumedAmount;
    }

}
