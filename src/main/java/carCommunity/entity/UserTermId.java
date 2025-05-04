package carCommunity.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTermId implements Serializable {

    private String userId;
    private String termCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTermId)) return false;
        UserTermId that = (UserTermId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(termCode, that.termCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, termCode);
    }
}
