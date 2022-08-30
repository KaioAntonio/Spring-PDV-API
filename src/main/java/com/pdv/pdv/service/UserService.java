package com.pdv.pdv.service;

import com.pdv.pdv.dto.UserDTO;
import com.pdv.pdv.entity.User;
import com.pdv.pdv.exceptions.NoItemException;
import com.pdv.pdv.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper mapper = new ModelMapper();

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user ->
                new UserDTO(user.getId(), user.getName(), user.isEnabled())).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO user){
        User userToSave = mapper.map(user,User.class);
        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.isEnabled());
    }

    public UserDTO findById(long id){
        Optional<User> optional = userRepository.findById(id);

        if(!optional.isPresent()){
            throw new NoItemException("Usuário não encontrado!");
        }
        User user = optional.get();
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());

    }

    public UserDTO update(UserDTO user){
        User userToSave = mapper.map(user,User.class);

        Optional<User> userToEdit = userRepository.findById(userToSave.getId());
        if(!userToEdit.isPresent()){
            throw new NoItemException("Usuário não encontrado!");
        }
        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.isEnabled());
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }

}
