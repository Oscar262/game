package org.game.army.character.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.game.army.character.inventory.costume.model.*;
import org.game.army.character.inventory.weapon.model.Weapon;

import javax.persistence.*;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InventoryCharacter {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @org.hibernate.annotations.Type(type = "json")
    @Column(
            name = "costume",
            columnDefinition = "jsonb"
    )
    private Map<Costume.Type, Costume> costume;

    @OneToOne(targetEntity = Weapon.class)
    @JsonProperty("principal_weapon")
    private Weapon principalWeapon;

    @OneToOne(targetEntity = Weapon.class)
    @JsonProperty("secondary_weapon")
    private Weapon secondaryWeapon;

    @OneToOne(mappedBy = "inventoryCharacter")
    private Character character;

}
