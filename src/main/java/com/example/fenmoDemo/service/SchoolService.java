package com.example.fenmoDemo.service;

import com.example.fenmoDemo.dao.AddressRepository;
import com.example.fenmoDemo.dao.SchoolRepository;
import com.example.fenmoDemo.entity.Address;
import com.example.fenmoDemo.entity.School;
import com.example.fenmoDemo.model.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private AddressRepository addressRepository;

    public School getById(Long id){
        if(id==null){
            return null;
        }
        Optional<School> school = schoolRepository.findById(id);
        return school.get();
    }

    public School getByRegistrationNumber(String regNumber){
        if(regNumber==null){
            return null;
        }
        try {
            Optional<School> school = schoolRepository.findByRegistrationNumber(regNumber);
            return school.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String createSchool(String schoolName, String registrationNumber, AddressDto addressDto){
        try {
            School school = new School();
            Address address = new Address();
            address.setState(addressDto.getState());
            address.setPincode(addressDto.getPincode());
            address.setLine2(addressDto.getLine2());
            address.setLine1(addressDto.getLine1());
            address.setCity(addressDto.getCity());
            address.setCountry(addressDto.getCountry());
            Address address1 = addressRepository.save(address);
            school.setAddress(address1.getId());
            school.setName(schoolName);
            school.setRegistrationNumber(registrationNumber);
            schoolRepository.save(school);
        }catch (Exception e){
            return e.getMessage();
        }
        return "CREATED";
    }
    public List<School> getAllSchools(){
        return schoolRepository.findAll();
    }
}
