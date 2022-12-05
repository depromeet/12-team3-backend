package com.depromeet.ahmatda.domain.user;

import com.depromeet.ahmatda.domain.BaseTimeEntity;
import com.depromeet.ahmatda.domain.category.Category;
import com.depromeet.ahmatda.domain.user.type.DeviceCode;
import com.depromeet.ahmatda.domain.user.type.UserStatusCode;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private DeviceCode deviceCode;

    private String deviceId;

    @Enumerated(value = EnumType.STRING)
    private UserStatusCode statusCd;

    @OneToMany(mappedBy = "user")
    private List<Category> categories = new ArrayList<>();

    public static User createUserWithDeviceId(DeviceCode deviceCode, String deviceId) {
        return User.builder()
                .deviceCode(deviceCode)
                .deviceId(deviceId)
                .statusCd(UserStatusCode.ACTIVATED)
                .build();
    }
}
