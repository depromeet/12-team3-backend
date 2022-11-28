package com.depromeet.ahmatda.domain.user;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.user.type.UserStatusCode;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;

    @Enumerated(value = EnumType.STRING)
    private UserStatusCode statusCd;

    public static User createUserWithDeviceId(String deviceId) {
        return User.builder()
                .deviceId(deviceId)
                .statusCd(UserStatusCode.ACTIVATED)
                .build();
    }
}
