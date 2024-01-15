package com.example.raghav.SpringSecurityClient.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
public class VerificationToken {

    private static final int EXPIRATION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN")
    )
    private User user;

    public VerificationToken(User user, String token){
        super();
        this.user = user;
        this.token = token;
        this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
    }

    public VerificationToken(String token){
        super();
        this.token = token;
        this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
    }

    private Date calculateExpirationTime(int expirationTime) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(new Date().getTime());
        calender.add(Calendar.MINUTE, expirationTime);
        return new Date(calender.getTime().getTime());
    }
}
