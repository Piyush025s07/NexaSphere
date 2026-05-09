package org.nexasphere.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "core_team_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreTeamMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String role;

    @NotBlank
    @Size(max = 100)
    private String branch;

    @NotBlank
    @Size(max = 20)
    @Column(name = "\"year\"")
    private String year;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z]$", message = "Section must be a single letter")
    private String section;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "WhatsApp number must be exactly 10 digits")
    private String whatsapp;

    @Size(max = 255)
    @Pattern(regexp = "^(https?://.+)?$", message = "LinkedIn must be a valid URL")
    private String linkedin;

    @Size(max = 255)
    @Pattern(regexp = "^(https?://.+)?$", message = "Instagram must be a valid URL")
    private String instagram;

    @Size(max = 500)
    @Pattern(regexp = "^(https?://.+)?$", message = "Photo URL must be a valid URL")
    private String photoUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
