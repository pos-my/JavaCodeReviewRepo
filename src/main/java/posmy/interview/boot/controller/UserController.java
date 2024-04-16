package posmy.interview.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import posmy.interview.boot.model.User;
import posmy.interview.boot.repository.UserRepository;
import posmy.interview.boot.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserController {

    //Why Autowired here.
    //What injection is this, and how many type of injection do you know ?
    @Autowired
    public UserService userService;
    @Autowired
    public UserRepository repository;


    //Questions
    //Review and Suggestion better approach.
    //Why return type as ResponseEntity , what benefits it provide?
    @GetMapping("/viewMember")
    public ResponseEntity<User> viewSingleMember() {
        for (User user : repository.findAll()) {
            if(user.getUserName() == "Geoffrey") {
                return ResponseEntity.ok().body(user);
            }
        }
        return ResponseEntity.notFound().build();
    }


    //Questions
    //Review and Suggestion better approach.
    //Explain Method Referencing ?
    //What is the expected outcome of this api?
    @GetMapping("/getMemberName")
    public List<String> getMemberName() {
        List<User> user = (List<User>) repository.findAll();
        List<String> result = user.stream().map(User::getUserName).collect(Collectors.toList());
        result.add("Kelwin Lee");

        List<String> list2 = List.of("Geoffrey", "Spaghetti");
        if(result.stream().filter(s -> !s.equals("Houston")).findFirst().isPresent()) {
            list2.add("Houston");
        }

        result.addAll(list2);
        return list2;
    }

    //Questions
    //Review and Suggestion better approach.
    //Which catch block it will fall into when hit errors?
    //What is the expected outcome of this api, provided json context below.
    /*
        {
            "userId": 12,
                "userName": null,
                "userPassword": "examplePassword",
                "userRole": "exampleRole"
        }
    */
    @PostMapping("/addMember")
    public ResponseEntity<User> addMember(@RequestBody User user){
        try {
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }
            if (user.getUserName() == null || user.getUserName().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            User addedUser = userService.addMember(user);

            return ResponseEntity.ok(addedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
            return ResponseEntity.ok(new User());
        }
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
