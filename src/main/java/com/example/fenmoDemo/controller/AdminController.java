package com.example.fenmoDemo.controller;

import com.example.fenmoDemo.entity.School;
import com.example.fenmoDemo.entity.User;
import com.example.fenmoDemo.enums.EnumUserType;
import com.example.fenmoDemo.model.AddressDto;
import com.example.fenmoDemo.model.UserDto;
import com.example.fenmoDemo.service.SchoolService;
import com.example.fenmoDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/attendanceManagement")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SchoolService schoolService;

    @Value("${app.admin.username}")
    private String finalAdminUsername;

    @Value("${app.admin.password}")
    private String finalAdminPassword;

    @GetMapping
    public String pre(Model model){
        return "login";
    }

    @RequestMapping(value = "/loggedInUser",method = {RequestMethod.POST,RequestMethod.GET})
    public String login(@RequestParam("userTypeId") Long userTypeId,
                        Model model,
                        RedirectAttributes redirectAttributes,
                        @RequestParam(value = "email",required = false) String email,
                        @RequestParam(value = "adminPassword",required = false) String adminPassword){
        EnumUserType enumUserType = EnumUserType.getEnumUserTypeById(userTypeId);

        if(enumUserType.getName().equals(EnumUserType.ADMIN.getName())){
            if(adminPassword.isBlank() || email.isBlank() || !email.equals(finalAdminUsername) || !adminPassword.equals(finalAdminPassword)){
                redirectAttributes.addFlashAttribute("error","invalid credential");
                return "redirect:/attendanceManagement";
            }
            model.addAttribute("schools",schoolService.getAllSchools());
            model.addAttribute("userTypeId",EnumUserType.ADMIN.getId());
            return "admindashBoard";

        }else{
            User loggedInUser = userService.getUserByEmail(email);
            if(loggedInUser == null){
                redirectAttributes.addFlashAttribute("error","invalid credential");
                return "redirect:/attendanceManagement";
            }
            School school = schoolService.getById(loggedInUser.getSchoolId());
            model.addAttribute("user",loggedInUser);
            model.addAttribute("school",school);
            if(EnumUserType.getEnumUserTypeById(loggedInUser.getUserTypeId()).getId().equals(EnumUserType.TEACHER.getId())){
                return "teacherdashBoard";
            }else{
                return "studentdashBoard";
            }

        }
    }

    @GetMapping("/loggedIn")
    public String loggedIn(Model model,@RequestParam Long userTypeId){
        EnumUserType enumUserType = EnumUserType.getEnumUserTypeById(userTypeId);
        model.addAttribute("userType",enumUserType);
        if(enumUserType.getName().equals(EnumUserType.ADMIN.getName())){
            model.addAttribute("schools",schoolService.getAllSchools());
        }
        return "admindashBoard.html";
    }

    @PostMapping("/addUser")
    @ResponseBody
    public String addUser(@RequestParam UserDto user){
        userService.createUser(user);
        return "Created";
    }

    @PostMapping("/addSchool")
    public String addSchool(@RequestParam String schoolName,
                            @ModelAttribute("addressDto") AddressDto addressDto,
                            @RequestParam String registrationNumber,
                            @RequestParam Long userTypeId,RedirectAttributes redirectAttributes
                             ){
        if(!userTypeId.equals(EnumUserType.ADMIN.getId())){
            redirectAttributes.addFlashAttribute("error","Not Authorized to add School");
            return "redirect:/attendanceManagement";
        }
        if(schoolService.getByRegistrationNumber(registrationNumber) != null){
            redirectAttributes.addFlashAttribute("error","Not Authorized to add School");
        }
        String message = schoolService.createSchool(schoolName,registrationNumber,addressDto);
        if(message.equals("CREATED")){
            redirectAttributes.addFlashAttribute("success","School CreatedSuccessFully");
        }
        redirectAttributes.addAttribute("userTypeId",EnumUserType.ADMIN.getId());
        redirectAttributes.addAttribute("email",finalAdminUsername);
        redirectAttributes.addAttribute("adminPassword",finalAdminPassword);
        return "redirect:/attendanceManagement/loggedInUser";
    }


}
