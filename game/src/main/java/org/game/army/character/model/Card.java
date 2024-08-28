package org.game.army.character.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] image;

    private Type type;

    private Integer starts;

    @Transient
    @JsonProperty("character_name")
    private String characterName;

    @Transient
    @JsonProperty("character_last_name")
    private String characterLastName;

    @Transient
    @JsonProperty("character_description")
    private String characterDescription;

    @Transient
    @JsonProperty("character_attributes")
    private Map<Character.Attribute, Long> characterAttributes;


    public enum Type{
        BRONZE(0),
        SILVER(1),
        GOLD(2),
        PLATINUM(3),
        AMETHYST(4),
        EMERALD(5),
        SAPPHIRE(6),
        RUBY(7),
        DIAMOND(8),
        DARK(9);

        private Integer stars;

        Type(Integer stars) {
            this.stars = stars;
        }

        public Integer getStars() {
            return stars;
        }

        public void setStars(Integer stars) {
            this.stars = stars;
        }
    }

}
