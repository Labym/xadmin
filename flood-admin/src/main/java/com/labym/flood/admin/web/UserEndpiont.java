package com.labym.flood.admin.web;


import com.labym.flood.admin.model.entity.User;
import com.labym.flood.admin.repository.UserRepository;
import com.labym.flood.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/users")
public class UserEndpiont {

    private final UserRepository userRepository;

    public UserEndpiont(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable Long userId){
        return ResponseUtil.wrapOrNotFound(userRepository.findById(userId));
    }
}
