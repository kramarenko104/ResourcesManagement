package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.Resource;
import com.gmail.kramarenko104.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class CommonResourceService{

    EntityManager em;
    UserActionsLogService log;

    public CommonResourceService(){}

    public CommonResourceService(UserActionsLogService log, EntityManager em){
        this.em = em;
        this.log = log;
    }
    public int getResource(Resource resource, int takeAmount){
        em.getTransaction().begin();
        int tookAmount = resource.takeResource(takeAmount);
        if (tookAmount > 0) {
            em.merge(resource);
        }
        em.getTransaction().commit();
        return tookAmount;
    }

    public void addResource(Resource resource, int addAmount){
        em.getTransaction().begin();
        resource.addResource(addAmount);
        em.merge(resource);
        em.getTransaction().commit();
    }

    public void removeResource(Resource resource, int amount){
        em.getTransaction().begin();
        resource.removeResource(amount);
        em.merge(resource);
        em.getTransaction().commit();
    }

    public List<Resource> listAllAvailableResources(){
        em.getTransaction().begin();
        try {
            TypedQuery<Resource> query = em.createNamedQuery("listAllAvailableResources", Resource.class);
            return query.getResultList();
        } finally {
            em.getTransaction().rollback();
        }
    }

    public void printListAllAvailableResources(User user){
        List<Resource> allResources = listAllAvailableResources();
        StringBuilder sb = new StringBuilder();
        sb.append(user.toString() + " prints all AvailableResources: ");
        for (Resource resource : allResources) {
            sb.append("[" + resource.getName() + ", left: " + resource.getLeftAmount()+ "] ");
        }
        log.recordUserAction(user, new Date(), sb.toString());
    }



}
