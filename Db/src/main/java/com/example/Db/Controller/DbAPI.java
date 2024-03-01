package com.example.Db.Controller;

import com.example.Db.Exception.UserNotFoundException;
import com.example.Db.Model.User;
import com.example.Db.Model.UserDTO;
import com.example.Db.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/db")
public class DbAPI {
    private UserRepository repo;
    DbAPI(UserRepository repo){
        this.repo=repo;
    }

    @PostMapping("/create")
    public UserDTO add(@RequestBody User user) {
        return new UserDTO(repo.save(user));
    }
    @PutMapping("/update")
    public UserDTO update(@RequestBody UserDTO user) throws UserNotFoundException {
        Optional<User> opt=repo.findById(user.getId());
        if (opt.isEmpty()) {
            throw new UserNotFoundException();
        }
        repo.deleteById(opt.get().getId());
        return new UserDTO(repo.save(new User(user.getId(),user.getName(), user.getEmail(), opt.get().getPassword())));
    }
    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Long id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);

        if (result.isPresent()) {
            return new UserDTO(result.get());
        }

        else throw new UserNotFoundException();
    }
    @GetMapping("/all")
    public List<UserDTO> list(){
        List<UserDTO> list=new ArrayList<>();
        for(User u: repo.findAll())list.add(new UserDTO(u));
        return list;
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws UserNotFoundException {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
        else throw new UserNotFoundException();
    }
}
