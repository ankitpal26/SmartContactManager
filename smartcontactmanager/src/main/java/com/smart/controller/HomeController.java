package com.smart.controller;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/home")
    public String home(Model model)
    {

        model.addAttribute("title","Home - Smart Contact Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model)
    {

        model.addAttribute("title","About - Smart Contact Manager");
        return "about";
    }
    @RequestMapping("/signup")
    public String signup(Model model)
    {

        model.addAttribute("title","Register - Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";
    }

    //this handler for register user

    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(value = "agreement",defaultValue = "false")
    boolean agreement, Model model ,  HttpSession session){


//        this field for agreement checkbox clicked or not  if it's checked then than u can submit form
//        but if it is not checked than it gives the massage that "checkbox is not checked"
        try {
            if(!agreement)
            {
                System.out.println("You have not agreed the term and conditions");
                throw  new Exception("You have not agreed the term and conditions");
            }

//if any error in name field than it is automatic store previous data in other field
            if (bindingResult.hasErrors())
            {
                System.out.println("Error" + bindingResult.toString());
                model.addAttribute("user",user);
                return  "signup";
            }

            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");



            System.out.println("Agreement "  +agreement);
            System.out.println("User " +user);

            User result = this.userRepository.save(user);

            model.addAttribute("user "+ result);

            model.addAttribute("user",new User());

//            set all value in session
            session.setAttribute("message",new Message("Successfully Registered !!" , "alert-success"));
            return "signup";

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("user",user);
            session.setAttribute("message",new Message("Something went wrong !!" + e.getMessage(), "alert-danger"));
            return "signup";
        }


    }
}
