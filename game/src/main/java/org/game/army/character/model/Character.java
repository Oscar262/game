package org.game.army.character.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.game.admin.model.User;
import org.game.army.character.inventory.costume.model.*;
import org.game.army.character.inventory.weapon.model.Weapon;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
public class Character {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = User.class)
    private User user;

    private String name;

    private byte[] image;

    private Long level;

    private Long experience;

    @JsonProperty("to_next_level")
    private Long toNextLevel;

    private BasicType type;

    @Type(type = "json")
    @Column(
            name = "skills",
            columnDefinition = "jsonb"
    )
   private Map<String, Long> skills;

   //private Map<Capabilities, Long> capabilities;

   //@JsonProperty("max_capabilities")
   //private Map<Capabilities, Long> maxCapabilities;

    private SubType subType;

    private Profession profession;

    @OneToOne(targetEntity = InventoryCharacter.class)
    private InventoryCharacter inventoryCharacter;

    @Column(columnDefinition = "boolean default false")
    private boolean dead;


    public enum Capabilities{
        HEALTH_POINTS,
        MANA,
        AGILITY,
        STAMINA,
        ATTACK,
        SPEED,
        STRENGTH,
        MAGIC_POWER,
        MAGIC_RESISTANCE,
        ARMOR,
        LEADERSHIP
    }


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

        public List<SubType> getSubTypes() {
            return subTypes;
        }

        public void setSubTypes(List<SubType> subTypes) {
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
