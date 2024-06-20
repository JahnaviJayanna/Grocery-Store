package io.swagger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Table(name = "USERS")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @Pattern(regexp = "^US\\.\\d{13}$")
    @Column(nullable = false, updatable = false)
    private String userId = null;

    @Column(name = "FIRST_NAME", nullable = false)
    @NotEmpty
    private String firstName = null;

    @Column(name = "LAST_NAME")
    private String lastName = null;
    @Email
    @Column(name = "EMAIL_ID",unique = true)
    private String emailId = null;

    @Column(name = "PASSWORD", nullable = false)
    @NotEmpty
    private String password = null;

    @Column(name = "MSISDN",unique = true, nullable = false)
    @NotEmpty
    private String msisdn = null;

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @NotEmpty
    private Date dob = null;

    @Column(name = "STATUS")
    private String status = "ACTIVE";

    @Column(name = "USER_NAME", unique = true, nullable = false)
    @NotEmpty
    private String userName = null;

    @Column(name = "USER-ROLE", nullable = false)
    @NotEmpty
    private String userRole = null;

    @Column(name = "LAST_LOGIN")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String lastLogin = null;

    public Users(String userId, String firstName, String lastName, String emailId, String password, String msisdn, Date dob, String userName, String userRole) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = password;
        this.msisdn = msisdn;
        this.dob = dob;
        this.userName = userName;
        this.userRole = userRole;
    }


}
