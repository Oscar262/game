package org.game.army.character.model;


import org.game.admin.model.User;
import org.game.army.weapon.model.Weapon;

import javax.persistence.*;
import java.util.List;

@Entity
public class Character {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = User.class)
    private User user;

    private String name;

    private BasicType type;

    private SubType subType;

    private Profession profession;

    @OneToOne(targetEntity = Weapon.class)
    private Weapon principalWeapon;

    @OneToOne(targetEntity = Weapon.class)
    private Weapon secundaryWeapon;

    @Column(columnDefinition = "boolean default false")
    private boolean dead;



    public enum BasicType {
        PIRATE(List.of(Character.SubType.GUNNER, Character.SubType.PIRATE_KNIGHT)),
        SOLDIER(List.of(Character.SubType.KNIGHT, Character.SubType.HEAVY_KNIGHT)),
        RANGE(List.of(Character.SubType.SHOOTER, Character.SubType.ARCHER)),
        MAGICIAN(List.of(Character.SubType.ELEMENTAL_MAGE, Character.SubType.HEALER, Character.SubType.SUMMONER)),
        DRUID(List.of(Character.SubType.DARK_DRUID, Character.SubType.LIGHT_DRUID)),
        STEALER(List.of(Character.SubType.KILLER)),
        HALF_BEAST(List.of(Character.SubType.BEAST_TAMER)),
        SUMMONED(List.of(Character.SubType.ALIVE_SUMMONED));


        private List<Character.SubType> subTypes;

        BasicType(List<Character.SubType> subTypes) {
            this.subTypes = subTypes;
        }
    }

    public enum Profession {
        CHEF,
        BLACKSMITH,
        BARD,
        HUNTER,
        EXPLORER,
        ENCHANTER,
        ALCHEMIST,
        BUILDER,
        INSTRUCTOR
    }

    public enum SubType {
        HEALER,
        ARCHER,
        SUMMONER,
        KILLER,
        SHOOTER,
        GUNNER,
        PIRATE_KNIGHT,
        ELEMENTAL_MAGE,
        LIGHT_DRUID,
        DARK_DRUID,
        KNIGHT,
        HEAVY_KNIGHT,
        BEAST_TAMER,
        ALIVE_SUMMONED;
    }
}
