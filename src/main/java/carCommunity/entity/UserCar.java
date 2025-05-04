package carCommunity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(UserCarId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCar {

    @Id
    @Column(name = "User_ID", length = 50)
    private String userId;

    @Id
    @Column(name = "Car_ID")
    private Integer carId;

    @Column(name = "Image_Path", length = 255)
    private String imagePath; // 서버에 저장된 3D 이미지 경로
}