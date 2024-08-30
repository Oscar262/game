package org.game.army.character.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.game.auth.model.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Skill {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private byte[] image;

    @Column(columnDefinition = "varchar(1000)")
    private String description;

    @JsonProperty("enabled_for_sub_types")
    @Transient
    private List<Character.SubType> enabledForSubTypes;

    @Type(type = "json")
    @Column(
            name = "enabled_for_sub_types",
            columnDefinition = "jsonb"
    )
    private List<Integer> enabledForSubTypesBBDD;


    @JsonProperty("enabled_for_basic_types")
    @Transient
    private List<Character.BasicType> enabledForBasicTypes;


    @Type(type = "json")
    @Column(
            name = "enabled_for_basic_types",
            columnDefinition = "jsonb"
    )
    private List<Integer> enabledForBasicTypesBBDD;

    public List<Character.BasicType> getEnabledForBasicTypes() {
        List<Character.BasicType> basicTypes = new ArrayList<>();
        for (Integer ordinal : enabledForBasicTypesBBDD) {
            basicTypes.add(Character.BasicType.values()[ordinal]);
        }
        return basicTypes;
    }

    public List<Character.SubType> getEnabledForSubTypes() {
        List<Character.SubType> basicTypes = new ArrayList<>();
        for (Integer ordinal : enabledForSubTypesBBDD) {
            basicTypes.add(Character.SubType.values()[ordinal]);
        }
        return basicTypes;
    }
}
