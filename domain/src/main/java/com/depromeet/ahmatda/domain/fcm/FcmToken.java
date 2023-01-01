package com.depromeet.ahmatda.domain.fcm;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.user.User;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmToken extends BaseTimeEntity {

    @Id
    private Long userId;

    @Column
    private String fcmToken;
}
