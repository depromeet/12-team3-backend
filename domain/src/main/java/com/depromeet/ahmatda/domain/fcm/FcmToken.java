package com.depromeet.ahmatda.domain.fcm;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;

@Getter
@Entity
public class FcmToken extends BaseTimeEntity {

    @Id
    @OneToOne
    private User user;

    @Column
    private String fcmToken;
}
