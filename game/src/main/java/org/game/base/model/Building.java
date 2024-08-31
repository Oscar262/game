package org.game.base.model;

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
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Type type;

    @JsonProperty("image")
    @org.hibernate.annotations.Type(type = "json")
    @Column(
            name = "image",
            columnDefinition = "jsonb"
    )
    private Map<Long, byte[]> image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = MainBase.class)
    @JoinColumn(name = "main_base")
    private MainBase mainBase;

    public enum Type{
        RESIDENCE,
        INN,
        MAGIC_TOWER,
        ARMORY,
        SMITHY,
        LABORATORY,
        TRAINING_CENTER,
        MISSION_CENTER
    }
}
