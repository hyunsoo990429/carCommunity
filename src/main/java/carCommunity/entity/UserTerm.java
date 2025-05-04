package carCommunity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@IdClass(UserTermId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTerm {

    @Id
    @Column(name = "User_ID", length = 50)
    private String userId;

    @Id
    @Column(name = "Term_Code", length = 10)
    private String termCode;

    @Column(name = "Is_Agreed")
    private Boolean isAgreed = false;

    @Column(name = "Created_At")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "Updated_At")
    private LocalDateTime updatedAt = LocalDateTime.now();
}