package org.game.army.character.inventory.costume.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Boots {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
