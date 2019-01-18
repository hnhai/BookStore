package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.activemq.Email;
import com.framgia.bookStore.activemq.Sender;
import com.framgia.bookStore.constants.RoleType;
import com.framgia.bookStore.dto.user.RegisterForm;
import com.framgia.bookStore.entity.RoleEntity;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.entity.UserRoleEntity;
import com.framgia.bookStore.entity.UserRoleId;
import com.framgia.bookStore.repository.RoleRepository;
import com.framgia.bookStore.repository.UserRepository;
import com.framgia.bookStore.repository.UserRoleRopository;
import com.framgia.bookStore.service.UserService;
import com.framgia.bookStore.util.WebUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRopository userRoleRopository;

    @Autowired
    private Sender sender;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsernameAndDeleted(username, false);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmailAndDeleted(email, false);
    }

    @Override
    @Transactional
    public UserEntity saveUser(RegisterForm form) {
        UserEntity user = new UserEntity();
        user.setEmail(form.getEmail().trim());
        user.setUsername(form.getUsername().trim());
        user.setPassword(passwordEncoder.encode(form.getPassword().trim()));
        user.setGender(form.getGender());
        user.setEnabled(true);
        user.setDeleted(false);
        user.setStatus(true);
        user = userRepository.save(user);
        RoleEntity role = roleRepository.findByRoleType(RoleType.ROLE_USER);
        UserRoleEntity userRole = new UserRoleEntity(user, role);
        userRole.setId(new UserRoleId(user.getId(), role.getId()));
        userRole.setDeleted(false);
        userRoleRopository.save(userRole);
        return user;
    }

    @Override
    public UserEntity findByUsernameAndToken(String username, String token) {
        return userRepository.findByUsernameAndTokenAndDeleted(username, token, false);
    }

    @Override
    public Boolean duplicateEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean duplicateUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    @Transactional
    public Boolean resetPassword(String usernameOrEmail, HttpServletRequest request) {
        UserEntity user = userRepository.findByUsernameAndDeleted(usernameOrEmail, false);
        if(user == null){
            user = userRepository.findByEmailAndDeleted(usernameOrEmail, false);
        }
        if (user != null){
            String token = RandomStringUtils.random(45, true, false);
            user.setToken(token);
            Email email = new Email();
            email.setFrom("teststackjava@gmail.com");
            email.setSubject("Reset Password");
            email.setTo(user.getEmail());
            email.setTemplate("/page/reset-password");
            String linkReset = WebUtil.getBaseUrl(request) + "/update-password/" + user.getUsername() + "/" + user.getToken();
            email.getVars().put("username", user.getUsername());
            email.getVars().put("url", linkReset);
            sender.send(email);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public UserEntity updatePassword(Long userId, String password) {
        UserEntity user = userRepository.findByIdAndDeleted(userId, false).get();
        if (user != null){
            user.setPassword(passwordEncoder.encode(password));
            user.setToken(null);
            user = userRepository.save(user);
            return user;
        }
        return null;
    }
}
