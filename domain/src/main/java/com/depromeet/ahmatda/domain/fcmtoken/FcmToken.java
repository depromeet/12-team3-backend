package com.depromeet.ahmatda.domain.fcmtoken;

import com.depromeet.ahmatda.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;

@Getter
@Entity
public class FcmToken {

    @Id
    @OneToOne
    private User user;

    @Column
    private String fcmToken;
}
