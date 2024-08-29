package org.game.army.character.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] image;

    private Type type;

    private Integer stars;

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
        BRONZE(5),
        SILVER(20),
        GOLD(50),
        DIAMOND(75),
        DARK(100);

        private Integer maxLevel;

        Type(Integer maxLevel) {
            this.maxLevel = maxLevel;
        }

        public Integer getMaxLevel() {
            return maxLevel;
        }

        public void setMaxLevel(Integer maxLevel) {
            this.maxLevel = maxLevel;
        }
    }

}
