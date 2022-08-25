package com.pdv.pdv.controller;

import com.pdv.pdv.dto.ResponseDTO;
import com.pdv.pdv.dto.UserDTO;
import com.pdv.pdv.entity.User;
import com.pdv.pdv.exceptions.NoItemException;
import com.pdv.pdv.repository.UserRepository;
import com.pdv.pdv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(@Autowired UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody User user){
        try{
            user.setEnabled(true);
            return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity put(@RequestBody User user){
        try{
            return new ResponseEntity(userService.update(user), HttpStatus.OK);

        } catch (NoItemException error){
            return new ResponseEntity(new ResponseDTO<>(error.getMessage(), user), HttpStatus.BAD_REQUEST);
        } catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO<>(error.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        try{
            userService.deleteById(id);
            return new ResponseEntity<>("User with id: " + id + " was deleted", HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
