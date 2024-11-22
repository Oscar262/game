package org.game.army.character.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        NONE(1L),
        BRONZE(5L),
        SILVER(20L),
        GOLD(50L),
        EPIC(75L),
        LEGENDARY(100L),
        DARK(100L);

        private Long minLevel;

        Type(Long minLevel) {
            this.minLevel = minLevel;
        }

        public Long getMinLevel() {
            return minLevel;
        }

        public void setMinLevel(Long minLevel) {
            this.minLevel = minLevel;
        }
    }

}
