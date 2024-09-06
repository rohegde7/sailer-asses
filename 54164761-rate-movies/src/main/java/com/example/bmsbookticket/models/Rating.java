package com.example.bmsbookticket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Rating extends BaseModel{
    @ManyToOne
    // M : 1
    private User user;
    @ManyToOne
    private Movie movie;
    private int rating;
}
