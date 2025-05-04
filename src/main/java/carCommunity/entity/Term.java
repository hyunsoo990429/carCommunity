package carCommunity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Term")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Term {

    @Id
    @Column(name = "Term_Code", length = 10)
    private String termCode;

    @Column(name = "Term_Content", nullable = false, columnDefinition = "TEXT")
    private String termContent;
}