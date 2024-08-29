package org.game.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Type type;

    public enum Type{
        RESIDENCE,
        INN,
        MAGIC_TOWER,
        ARMORY,
        SMITHY,
        LABORATORY,
        TRAINING_CENTER,
        MISSION_CENTER
    }
}
