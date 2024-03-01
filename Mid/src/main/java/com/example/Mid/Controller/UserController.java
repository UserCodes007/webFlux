package com.example.Mid.Controller;

import com.example.Mid.Exception.UserNotFoundException;
import com.example.Mid.Model.User;
import com.example.Mid.Model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    WebClient webClient;
    UserController(WebClient webClient){
        this.webClient=webClient;
    }
    @PostMapping("/add")
    public Mono<ResponseEntity<?>> add(@RequestBody User user) {
        Mono<UserDTO> ref= webClient.post().uri("/create").body(Mono.just(user),User.class).retrieve().bodyToMono(UserDTO.class);
        //ref.subscribe();
        return ref.map(i->ResponseEntity.created(URI.create("/users/" + i.getId())).body(i));
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<?>> get(@PathVariable("id") Long id) {
        Mono<UserDTO> ref= webClient.get().uri("/"+id).retrieve().onStatus(status -> status == HttpStatus.NOT_FOUND,
                response -> Mono.just(new UserNotFoundException())).bodyToMono(UserDTO.class);
        //ref.subscribe();
        return ref.map(i->ResponseEntity.ok().body(i));
    }


    @GetMapping
    public Mono<ResponseEntity<?>> list() {
        Mono<List> ref= webClient.get().uri("/all").retrieve().bodyToMono(List.class);
        //ref.subscribe();
        return ref.map(i->{
            if(((List<UserDTO>)i).isEmpty())return ResponseEntity.noContent().build();
            return ResponseEntity.ok().body((List<UserDTO>)i);
        });
    }


    @PutMapping("/update")
    public Mono<ResponseEntity<?>> update( @RequestBody User user) {
        Mono<UserDTO> ref= webClient.put().uri("/update").body(Mono.just(user),User.class).retrieve().onStatus(status -> status == HttpStatus.NOT_FOUND,
                response -> Mono.just(new UserNotFoundException())).bodyToMono(UserDTO.class);
        //ref.subscribe();
        return ref.map(i -> ResponseEntity.ok().body(i));
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> delete(@PathVariable("id")  Long id) {
        Mono<Void> ref= webClient.delete().uri("/"+id).retrieve().onStatus(status -> status == HttpStatus.NOT_FOUND,
                response -> Mono.just(new UserNotFoundException())).bodyToMono(Void.class);
        //ref.subscribe();
        return ref.map(i->ResponseEntity.noContent().build());
    }
}
