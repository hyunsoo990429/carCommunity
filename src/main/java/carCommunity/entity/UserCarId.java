package carCommunity.entity;

import lombok.*;

import java.io.Serializable; //직렬화를 위한 import jpa로 맵핑할때 복합키가 있는 테이블에서는 필수이다.
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserCarId implements Serializable {

    private String userId;
    private Integer carId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCarId)) return false;
        UserCarId that = (UserCarId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(carId, that.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, carId);
    }
}