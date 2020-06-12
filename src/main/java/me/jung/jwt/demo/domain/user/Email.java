package me.jung.jwt.demo.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Email {
    @javax.validation.constraints.Email
    @Column(name="email",nullable = false,unique = true)
    private String value;

    @Builder
    public Email(String value) {
        this.value = value;
    }
}
