package com.example.Db.Model;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private  String name;
    private Long id;
    private String email;
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDTO other = (UserDTO) obj;
        return Objects.equals(id, other.id);
    }
    public UserDTO(User u){
        this.name=u.getName();
        this.id=u.getId();
        this.email=u.getEmail();
    }
    }