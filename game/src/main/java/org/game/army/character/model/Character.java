package org.game.army.character.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.game.auth.model.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Character {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = User.class)
    private User user;

    private Gender gender;

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
   private Map<Skill, Qualification> skills;

    @Type(type = "json")
    @Column(
            name = "attributes",
            columnDefinition = "jsonb"
    )
   private Map<Attribute, Long> attributes;

   @JsonProperty("max_attributes")
   @Type(type = "json")
   @Column(
           name = "max_attributes",
           columnDefinition = "jsonb"
   )
   private Map<Attribute, Long> maxAttributes;

    private SubType subType;

    @JsonProperty("profession")
    @Type(type = "json")
    @Column(
            name = "profession",
            columnDefinition = "jsonb"
    )
    private Map<Profession, Qualification> profession;

    @OneToOne(targetEntity = InventoryCharacter.class)
    private InventoryCharacter inventoryCharacter;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Card.class)
    private Card card;

    @Column(columnDefinition = "boolean default false")
    private boolean dead;

    @Column(columnDefinition = "boolean default false")
    private boolean favorite;



    public enum Qualification {
        F,
        E,
        D,
        C,
        B,
        A,
        S,
        X
    }

    public enum Attribute{
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
        PIRATE(List.of(SubType.GUNNER, SubType.PIRATE_KNIGHT)),
        SOLDIER(List.of(SubType.KNIGHT, SubType.HEAVY_KNIGHT)),
        RANGE(List.of(SubType.SHOOTER, SubType.ARCHER)),
        MAGICIAN(List.of(SubType.ELEMENTAL_MAGE, Character.SubType.HEALER, SubType.SUMMONER)),
        DRUID(List.of(SubType.DARK_DRUID, SubType.LIGHT_DRUID)),
        STEALER(List.of(SubType.KILLER)),
        HALF_BEAST(List.of(SubType.BEAST_TAMER, SubType.TURNED)),
        SUMMONED(List.of(SubType.ALIVE_SUMMONED));


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
        TURNED,
        ALIVE_SUMMONED;
    }
    //TODO: probably class
    public enum Skill{

    }

    public enum Gender{
        MALE,
        FEMALE
    }
}
