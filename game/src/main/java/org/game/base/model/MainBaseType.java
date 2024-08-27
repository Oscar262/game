package org.game.base.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.game.army.character.model.Character;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class MainBaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Type type;

    @OneToMany(mappedBy = "mainBaseType")
    private Set<MainBase> mainBaseList;

    @JsonProperty("forbidden_characters")
    @org.hibernate.annotations.Type(type = "json")
    @Column(
            name = "forbidden_characters",
            columnDefinition = "jsonb"
    )
    private List<Character.BasicType> forbiddenCharacters;

    private int level;



    public enum Type {
        MODERN_MILITARY,
        CASTLE,
        MAGICIANS_CASTLE,
        PIRATE_SHIP,
        MODERN_SHIP
    }

}
