package com.gmail.kramarenko104.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String ipAddress;

    @OneToMany (mappedBy = "user")
    private List<UserActionLogRecord> records;

    public User() {
    }

    public List<UserActionLogRecord> getRecords() {
        return records;
    }

    public void setRecords(List<UserActionLogRecord> records) {
        this.records = records;
    }

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return ("'" + name + "', ipAddress='" + ipAddress + "'");
    }
}
