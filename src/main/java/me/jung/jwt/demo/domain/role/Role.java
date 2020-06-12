package me.jung.jwt.demo.domain.role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Table(name="ROLE")
@Getter
@Entity
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROLE_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private RoleName name;

    @Builder
    public Role(RoleName name) {
        this.name = name;
    }
}
