package com.airbnb.entity;

import jakarta.persistence.*;

@Entity
@Table(name="favourite")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private long id;

    @Column(name="is_favourite", nullable = false)
    private Boolean isFavourite=false;

    @ManyToOne
    @JoinColumn(name="property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name="property_user_id")
    private PropertyUser propertyUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public PropertyUser getPropertyUser() {
        return propertyUser;
    }

    public void setPropertyUser(PropertyUser propertyUser) {
        this.propertyUser = propertyUser;
    }
}
