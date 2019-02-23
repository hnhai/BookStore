package com.framgia.bookStore.controller;

import com.framgia.bookStore.auth.CustomUserDetail;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.form.Profile;
import com.framgia.bookStore.form.Register;
import com.framgia.bookStore.form.UpdatePassword;
import com.framgia.bookStore.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController extends BaseController {

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid Profile profile, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "redirect:/profile?error";
        }
        UserEntity user = userService.findByUsername(SecurityUtil.getCurrentUser().getUsername());
        if (user == null){
            return "redirect:/profile?error";
        }
        if(!userService.updateProfile(user, profile)){
            return "redirect:/profile?error";
        }
        return "redirect:/profile";
    }

    @GetMapping("/login")
    public String loginPage() {
        CustomUserDetail user = SecurityUtil.getCurrentUser();
        if (user != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerView() {
        CustomUserDetail user = SecurityUtil.getCurrentUser();
        if (user != null) {
            return "redirect:/";
        }
        return "/register";
    }

    @PostMapping("/register")
    public String registerProcess(@Valid Register form, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("RegisterForm", form);
            return "redirect:/register";
        }
        if (userService.saveUser(form, request) != null) {
            UserEntity user = userService.findByUsername(form.getUsername());
            CustomUserDetail userDetails = buildUser(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        }
        return "/home";
    }

    @PostMapping("/reset-password/{usernameOrPassword}")
    public ResponseEntity resetPassword(@PathVariable("usernameOrPassword") String usernameOrPassword, HttpServletRequest request){

        return new ResponseEntity<>(userService.resetPassword(usernameOrPassword, request), HttpStatus.OK);
    }

    @GetMapping("api-check-email/{email}")
    public ResponseEntity checkDuplicateEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(userService.duplicateEmail(email), HttpStatus.OK);
    }

    @GetMapping("api-check-username/{username}")
    public ResponseEntity checkDuplicateUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.duplicateUsername(username), HttpStatus.OK);
    }

    @GetMapping("/update-password/{username}/{token}")
    public String updatePasswordByToken(@PathVariable("username") String username, @PathVariable("token") String token, Model model){
        UserEntity user = userService.findByUsernameAndToken(username, token);
        if(user != null){
            model.addAttribute("userId", user.getId());
            return "/page/update-password";
        }
        return "redirect:/404";
    }

    @PostMapping("/update-password")
    public ResponseEntity updatePassword(@Valid UpdatePassword updatePassword, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity(Boolean.FALSE, HttpStatus.OK);
        }else{
            UserEntity user = userService.updatePassword(updatePassword.getId(), updatePassword.getPassword());
            if(user != null){
                CustomUserDetail userDetails = buildUser(user);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return new ResponseEntity(Boolean.TRUE, HttpStatus.OK);
            }
        }
        return new ResponseEntity(Boolean.FALSE, HttpStatus.OK);
    }

    private CustomUserDetail buildUser(UserEntity user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getUserRoleEntities().forEach(e -> authorities.add(new SimpleGrantedAuthority(e.getRoleEntity().getRoleType().toString())));
        CustomUserDetail userDetail = new CustomUserDetail(user, authorities);
        return userDetail;
    }
}
