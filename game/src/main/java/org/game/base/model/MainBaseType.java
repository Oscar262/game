package org.game.base.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.game.army.character.model.Character;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MainBaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Type type;

    @OneToMany(mappedBy = "mainBaseType")
    @JsonIgnore
    private Set<MainBase> mainBaseList;

    @JsonProperty("available_characters")
    @org.hibernate.annotations.Type(type = "json")
    @Column(
            name = "available_characters",
            columnDefinition = "jsonb"
    )
    private List<Character.BasicType> availableCharacter;

    private int level;



    public enum Type {
        MODERN_MILITARY,
        CASTLE,
        MAGICIANS_CASTLE,
        PIRATE_SHIP,
        MODERN_SHIP
    }

}
