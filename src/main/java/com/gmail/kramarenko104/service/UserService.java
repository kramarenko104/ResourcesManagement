package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.entity.User;
import javax.persistence.EntityManager;

public class UserService {

    EntityManager em;

    public UserService(EntityManager em){
        this.em = em;
    }

    public User getUser(int id) {
        em.getTransaction().begin();
        try {
            return em.find(User.class, id);
        } finally {
            em.getTransaction().rollback();
        }
    }

    public void saveUser(User user) {
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }



}
