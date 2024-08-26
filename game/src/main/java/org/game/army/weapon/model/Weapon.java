package org.game.army.weapon.model;

import org.game.admin.model.User;
import org.game.utils.StringListConverter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = User.class)
    private User user;

    private String name;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = StringListConverter.class)
    private List<String> skills;

    private Type type;

    public enum Type{
        SWORD,
        BOW,
        SPEAR,
        GUN,
        SHIELD,
        MAGIC
    }
}
