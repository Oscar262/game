package org.game.army.character.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.game.army.character.inventory.costume.model.Boots;
import org.game.army.character.inventory.costume.model.Gloves;
import org.game.army.character.inventory.costume.model.Helmet;
import org.game.army.character.inventory.costume.model.Pants;
import org.game.army.character.inventory.weapon.model.Weapon;

import javax.persistence.*;

@Entity
public class InventoryCharacter {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = Helmet.class)
    private Helmet helmet;

    @OneToOne(targetEntity = Gloves.class)
    private Gloves gloves;

    @OneToOne(targetEntity = Boots.class)
    private Boots boots;

    @OneToOne(targetEntity = Pants.class)
    private Pants pants;

    @OneToOne(targetEntity = Weapon.class)
    @JsonProperty("principal_weapon")
    private Weapon principalWeapon;

    @OneToOne(targetEntity = Weapon.class)
    @JsonProperty("secondary_weapon")
    private Weapon secondaryWeapon;

    @OneToOne(mappedBy = "inventoryCharacter")
    private Character character;

}
