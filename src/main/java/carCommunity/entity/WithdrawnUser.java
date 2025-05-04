package carCommunity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawnUser {

    @Id
    @Column(name = "User_ID", length = 50)
    private String userId;

    @Column(name = "Deleted_At")
    private LocalDateTime deletedAt = LocalDateTime.now();
}