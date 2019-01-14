package com.framgia.bookStore.controller;

import com.framgia.bookStore.auth.CustomUserDetail;
import com.framgia.bookStore.dto.user.RegisterForm;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/login")
    public String loginPage() {
        CustomUserDetail user = SecurityUtil.getCurrentUser();
        if (user != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/admin")
    public String adminView() {
        return "admin";
    }

    @GetMapping("/system")
    public String sysView() {
        return "system";
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
    public String registerProcess(@Valid RegisterForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("RegisterForm", form);
            return "redirect:/register";
        }
        if (userService.saveUser(form) != null) {
            UserEntity user = userService.findByUsername(form.getUsername());
            CustomUserDetail userDetails = buildUser(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        }
        return "/home";
    }

    @GetMapping("api-check-email/{email}")
    public ResponseEntity checkDuplicateEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(userService.duplicateEmail(email), HttpStatus.OK);
    }

    @GetMapping("api-check-username/{username}")
    public ResponseEntity checkDuplicateUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.duplicateUsername(username), HttpStatus.OK);
    }

    private CustomUserDetail buildUser(UserEntity user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getUserRoleEntities().forEach(e -> authorities.add(new SimpleGrantedAuthority(e.getRoleEntity().getRoleType().toString())));
        CustomUserDetail userDetail = new CustomUserDetail(user, authorities);
        return userDetail;
    }
}
