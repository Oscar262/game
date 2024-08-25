package org.game.base.model;

import org.game.utils.StringListConverter;

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

    @Column(columnDefinition = "jsonb")
    @Convert(converter = StringListConverter.class)
    private List<String> forbiddenCharacters;

    private int level;



    public enum Type {
        MODERN_MILITARY,
        CASTLE,
        MAGICIANS_CASTLE,
        PIRATE_SHIP,
        MODERN_SHIP
    }

}
