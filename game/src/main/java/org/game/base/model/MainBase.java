package org.game.base.model;

import javax.persistence.*;
import java.util.Map;

@Entity
public class MainBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long level;

    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = MainBaseType.class)
    @JoinColumn(name = "main_base_type")
    private MainBaseType mainBaseType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = MainBaseType.class)
    private Map<Building, Long> building;


}
