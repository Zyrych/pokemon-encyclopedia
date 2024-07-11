package com.dev.pokemon_encyclopedia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Item {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;
    private int cost;
    
}
