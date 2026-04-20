package com.example.fenmoDemo.service;

import com.example.fenmoDemo.dao.AddressRepository;
import com.example.fenmoDemo.dao.UserRepository;
import com.example.fenmoDemo.entity.Address;
import com.example.fenmoDemo.entity.School;
import com.example.fenmoDemo.entity.User;
import com.example.fenmoDemo.enums.EnumUserType;
import com.example.fenmoDemo.model.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SchoolService schoolService;

    public User createUser(UserDto user){
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        for(EnumUserType enumUserType: EnumUserType.values()){
            if(enumUserType.getId().equals(user.getUserTypeId())){
                newUser.setUserTypeId(enumUserType.getId());break;
            }
        }
        newUser.setEmail(newUser.getEmail());
        Address address = new Address();
        address.setCity(user.getAddressDto().getCity());
        address.setDistrict(user.getAddressDto().getCity());
        address.setLine1(user.getAddressDto().getLine1());
        address.setLine2(user.getAddressDto().getLine2());
        address.setPincode(user.getAddressDto().getPincode());
        address.setCountry(user.getAddressDto().getCountry());
        address.setState(user.getAddressDto().getState());
        newUser.setPhoneNumber(user.getPhoneNumber());
        Address address1 = addressRepository.save(address);
        newUser.setAddress(address1.getId());
        return  userRepository.save(newUser);
    }
    public List<User> createUsers(List<User> users){
        return userRepository.saveAll(users);
    }
    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public User updateUser(User user){
        Optional<User> existing = userRepository.findById(user.getId());
        User oldUser = null;
        if(existing.isPresent()){
            oldUser   = existing.get();
            oldUser.setFirstName(user.getFirstName());
            userRepository.save(oldUser);
        }else{
            return new User();
        }
        return oldUser;
    }
    public String deleteUser(long id){
        userRepository.deleteById(id);
        return "User got deletedd";
    }

}
