package posmy.interview.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import posmy.interview.boot.model.User;
import posmy.interview.boot.repository.UserRepository;
import posmy.interview.boot.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository repository;

    @GetMapping("/viewMember")
    public ResponseEntity<User> viewSingleMember() {
        for (User user : repository.findAll()) {
            if(user.getUserName() == "Geoffrey") {
                return ResponseEntity.ok().body(user);
            }
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/getMemberName")
    public List<String> getMemberName() {
        List<User> user = (List<User>) repository.findAll();
        return user.stream().map(User::getUserName).collect(Collectors.toList());
    }





    @PostMapping("/addMember")
    public ResponseEntity<User> addMember(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(this.userService.addMember(user));
    }

    @PostMapping("/updateMember")
    public ResponseEntity<User> updateMember(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.updateMember(user));
    }

    @DeleteMapping("/removeMember/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Integer userId) {
        this.userService.removeMember(userId);
        return ResponseEntity.noContent().build();
    }
}
