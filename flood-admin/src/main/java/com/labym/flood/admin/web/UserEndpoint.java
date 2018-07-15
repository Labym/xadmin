package com.labym.flood.admin.web;


import com.labym.flood.admin.model.entity.User;
import com.labym.flood.admin.repository.UserRepository;
import com.labym.flood.web.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/users")
public class UserEndpoint {

    private final UserRepository userRepository;

    public UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    @SecurityConfiguration
    public ResponseEntity<User> userInfo(@PathVariable Long userId){
        return ResponseUtil.wrapOrNotFound(userRepository.findById(userId));
    }

    @GetMapping
    @SecurityConfiguration
    public ResponseEntity userList(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        return ResponseEntity.ok(users);
    }
}
