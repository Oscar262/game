package org.game.base.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MainBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long level;

    @JsonProperty("image")
    @Type(type = "json")
    @Column(
            name = "image",
            columnDefinition = "jsonb"
    )
    private Map<Long, byte[]> image;

    private byte[] blazon;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = MainBaseType.class)
    @JoinColumn(name = "main_base_type")
    private MainBaseType mainBaseType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = MainBaseType.class)
    private List<Building> building;


}
