package com.example.raghav.SpringSecurityClient.Event;

import com.example.raghav.SpringSecurityClient.Entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ResendTokenEvent extends ApplicationEvent {
    private User user;
    private String resendTokenUrl;
    private String resendToken;

    public ResendTokenEvent(User user, String resendTokenUrl, String resendToken){
        super(user);
        this.user = user;
        this.resendTokenUrl = resendTokenUrl;
        this.resendToken = resendToken;
    }


}
