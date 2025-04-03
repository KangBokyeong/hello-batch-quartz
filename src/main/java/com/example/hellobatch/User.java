package com.example.hellobatch;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private boolean processed;
    @Column(name = "joined_at")
    private LocalDate joinedAt;

    // 기본 생성자 (JPA 필수)
    public User() {}

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean processed) { this.processed = processed; }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }
    public void setJoinedAt(LocalDate joinedAt) {
        this.joinedAt = joinedAt;
    }

}
