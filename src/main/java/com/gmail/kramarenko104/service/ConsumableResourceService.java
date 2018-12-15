package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.ConsumableResource;
import com.gmail.kramarenko104.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class ConsumableResourceService extends CommonResourceService {

    EntityManager em;
    UserActionsLogService log;

    public ConsumableResourceService(){}

    public ConsumableResourceService(UserActionsLogService log, EntityManager em){
        super(log, em);
        this.em = em;
        this.log = log;
    }

    public void replenishResource(User user, ConsumableResource resource, int amount){
        super.addResource(resource, amount);
        String description = "Admin added " + amount +
                " of ConsumableResource " + resource.toString() +
                ". Total count now: " + resource.getLeftAmount();
        log.recordUserAction(user, new Date(), description);
    }

    public void consumeResource(User user, ConsumableResource resource, int consumeAmount){
        int tookAmount = super.getResource(resource, consumeAmount);
        String description = "User " + user.toString() +
                ((tookAmount > 0) ? " consumed ":" couldn't consume ") +
                consumeAmount + " of " + resource.toString() +
                ". Total count now: " + resource.getLeftAmount() +
                ((resource.getLeftAmount() == 0) ? ".... NOTHING LEFT !!! NEED TO BUY?": "");
        log.recordUserAction(user, new Date(), description);
    }

    ///////////////////////////////////////////////////////////////////////

    public List<ConsumableResource> listConsumedResources(){
        em.getTransaction().begin();
        try {
            TypedQuery<ConsumableResource> query = em.createNamedQuery("listConsumedResources", ConsumableResource.class);
            return query.getResultList();
        } finally {
            em.getTransaction().rollback();
        }
    }

    public void printListConsumedResources(User user){
        List<ConsumableResource> resources = listConsumedResources();
        StringBuilder sb = new StringBuilder();
        sb.append(user.toString() + " prints all ConsumedResources: ");
        for (ConsumableResource resource : resources) {
            sb.append("[" + resource.getName() + ", count: " + resource.getConsumed()+ "] ");
        }
        log.recordUserAction(user, new Date(), sb.toString());
    }


    public List<ConsumableResource> listResourcesForConsumption(){
        em.getTransaction().begin();
        try {
            TypedQuery<ConsumableResource> query = em.createNamedQuery("listResourcesForConsumption", ConsumableResource.class);
            return query.getResultList();
        } finally {
            em.getTransaction().rollback();
        }
    }

    public void printListResourcesForConsumption(User user){
        List<ConsumableResource> resources = listResourcesForConsumption();
        StringBuilder sb = new StringBuilder();
        sb.append(user.toString() + " prints all resources for consumption: ");
        for (ConsumableResource resource : resources) {
            sb.append("[" + resource.getName() + ", count: " + resource.getLeftAmount()+ "] ");
        }
        log.recordUserAction(user, new Date(), sb.toString());
    }

}
