package org.game.admin.model;

import org.game.base.model.MainBase;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID publicId;

    private long level;

    @OneToOne(targetEntity = MainBase.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MainBase mainBase;


}
