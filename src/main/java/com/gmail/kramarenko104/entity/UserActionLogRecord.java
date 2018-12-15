package com.gmail.kramarenko104.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Table (name = "log_records")
public class UserActionLogRecord {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    private String description;

    private Date actionDate;

    @ElementCollection
    private Map<String, String> properties;

    @ManyToOne
    @JoinColumn(name="user_id", insertable = true, updatable = true)
    private User user;

    public UserActionLogRecord(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
