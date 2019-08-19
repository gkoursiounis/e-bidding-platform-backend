package com.Auctions.backEnd.responses;

import com.Auctions.backEnd.models.Geolocation;
import com.Auctions.backEnd.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@NoArgsConstructor
public class FormattedUser {
            private Long id;
    @Setter private String username;
    @Setter private String firstName;
    @Setter private String lastName;
    @Setter private String telNumber;
    @Setter private String taxNumber;
    @Setter private Boolean verified;
    @Setter private Boolean admin;
    @Setter private Geolocation location;
            private Date createdAt;

    public FormattedUser(User user) {
        this.id= user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.telNumber = user.getTelNumber();
        this.taxNumber = user.getTaxNumber();
        this.verified = user.isVerified();
        this.createdAt = user.getCreatedAt();
        this.location = user.getAddress();
        this.admin = user.isAdmin();
    }

    public FormattedUser(Long id, String userName, String firstName, String lastName,
                         String telNumber, String taxNumber, Boolean verified, Date createdAt,
                         Geolocation location, Boolean admin){
        this.id = id;
        this.username = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;
        this.taxNumber = taxNumber;
        this.verified = verified;
        this.location = location;
        this.admin = admin;
        this.createdAt = (Date) createdAt.clone();
    }


    public Date getCreatedAt() {
        return (Date) this.createdAt.clone();
    }


    @Override
    public boolean equals(Object o){
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
