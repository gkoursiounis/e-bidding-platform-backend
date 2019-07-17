package com.Auctions.backEnd.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "geolocation")
public class Geolocation extends AuditModel {

    @Size(max=512)
    private String apiIdentifier;

    private double longitude;

    private double latitude;

    private String locationType;

    private String locationTitle;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private final Set<Item> items = new TreeSet<>();
}
