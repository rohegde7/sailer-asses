package com.example.splitwise.models;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Expense extends BaseModel{
    private double amount;
    private Date addedAt;
    private String description;
    private String proofUrl;
    private List<ExpenseUser> expenseUsers;
}
