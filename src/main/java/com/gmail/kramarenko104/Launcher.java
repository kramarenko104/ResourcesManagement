package com.gmail.kramarenko104;

import com.gmail.kramarenko104.service.*;
import com.gmail.kramarenko104.entity.*;
import org.apache.log4j.PropertyConfigurator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Launcher {

    static {
        PropertyConfigurator.configure(Launcher.class.getResource("/log4j.properties"));
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnits.resourcesManagement");
        final EntityManager em = emf.createEntityManager();

        // create new users
        UserService userService = new UserService(em);
        final User admin = new User();
        admin.setId(1);
        admin.setName("admin");
        admin.setIpAddress("192.168.95.1");
        userService.saveUser(admin);

        final User user1 = new User();
        user1.setId(2);
        user1.setName("Ivanov Ivan Ivanovich");
        user1.setIpAddress("192.168.95.3");
        userService.saveUser(user1);

        final User user2 = new User();
        user2.setId(3);
        user2.setName("Petrenko Vasyl Nikolaevich");
        user2.setIpAddress("192.168.93.2");
        userService.saveUser(user2);

        UserActionsLogService log = new UserActionsLogService(em);

        // init set of Consumable Resources ////////////////////////////////////////
        ConsumableResourceService consServ = new ConsumableResourceService(log, em);

        // all these actions should be added to log
        ConsumableResource consRes1 = new ConsumableResource();
        consRes1.setId(1);
        consRes1.setName("Parker pen");
        consServ.replenishResource(admin, consRes1, 128);

        ConsumableResource consRes2 = new ConsumableResource();
        consRes2.setId(2);
        consRes2.setName("A4 paper block");
        consServ.replenishResource(admin, consRes2, 245);

        ConsumableResource consRes3 = new ConsumableResource();
        consRes3.setId(3);
        consRes3.setName("A4 folder");
        consServ.replenishResource(admin, consRes3, 355);

        // init set of Rentable Resources //////////////////////////////////
        RentableResourceService rentServ = new RentableResourceService(log, em);

        // all these actions should be added to log
        RentableResource rentRes1 = new RentableResource();
        rentRes1.setId(4);
        rentRes1.setName("table");
        rentServ.addResourceToInventory(admin, rentRes1, 345);

        RentableResource rentRes2 = new RentableResource();
        rentRes2.setId(5);
        rentRes2.setName("LAPTOP-JFR5JK2D");
        rentServ.addResourceToInventory(admin, rentRes2, 89);

        RentableResource rentRes3 = new RentableResource();
        rentRes3.setId(6);
        rentRes3.setName("book1");
        rentServ.addResourceToInventory(admin, rentRes3, 256);

        //////////////////////////////////////////////////////////////
        // all these actions should be added to log
        consServ.consumeResource(user1, consRes1, 7);
        consServ.consumeResource(user2, consRes1, 5);
        consServ.consumeResource(admin, consRes1, 150);
        consServ.printListConsumedResources(admin);
        consServ.printListResourcesForConsumption(admin);

        ////////////////////
        rentServ.checkoutResource(user1, rentRes2, 2);
        rentServ.checkoutResource(user2, rentRes2, 80);
        rentServ.checkinResource(user1, rentRes2, 1);
        rentServ.writeOffResourceFromInventory(admin, rentRes2, 1);
        rentServ.printListResourcesForRent(admin);

        // print to log list of all available resources
        CommonResourceService allServ = new CommonResourceService(log, em);
        allServ.printListAllAvailableResources(admin);

    }

}
