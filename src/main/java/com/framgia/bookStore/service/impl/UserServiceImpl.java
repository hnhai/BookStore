package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.activemq.Email;
import com.framgia.bookStore.activemq.Sender;
import com.framgia.bookStore.constants.MailConst;
import com.framgia.bookStore.constants.RoleType;
import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.entity.OrderDetailEntity;
import com.framgia.bookStore.entity.OrderDetailID;
import com.framgia.bookStore.entity.OrderEntity;
import com.framgia.bookStore.entity.PaymentEntity;
import com.framgia.bookStore.entity.RoleEntity;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.entity.UserRoleEntity;
import com.framgia.bookStore.entity.UserRoleId;
import com.framgia.bookStore.form.BookCart;
import com.framgia.bookStore.form.Profile;
import com.framgia.bookStore.form.Register;
import com.framgia.bookStore.repository.BookReponsitory;
import com.framgia.bookStore.repository.OrderDetailReponsitory;
import com.framgia.bookStore.repository.OrderReponsitory;
import com.framgia.bookStore.repository.PaymentReponsitory;
import com.framgia.bookStore.repository.RoleRepository;
import com.framgia.bookStore.repository.UserRepository;
import com.framgia.bookStore.repository.UserRoleRopository;
import com.framgia.bookStore.service.UserService;
import com.framgia.bookStore.util.SecurityUtil;
import com.framgia.bookStore.util.WebUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private OrderReponsitory orderReponsitory;

    @Autowired
    private PaymentReponsitory paymentReponsitory;

    @Autowired
    private OrderDetailReponsitory orderDetailReponsitory;

    @Autowired
    private BookReponsitory bookReponsitory;

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
    public UserEntity saveUser(Register form, HttpServletRequest request) {
        UserEntity user = new UserEntity();
        user.setEmail(form.getEmail().trim());
        user.setUsername(form.getUsername().trim());
        if (StringUtils.isEmpty(form.getPassword())){
            String passwordRandom = RandomStringUtils.random(10, true, false);
            form.setPassword(passwordRandom);
            Email email = new Email();
            email.setFrom("bookstore@gmail.com");
            email.setSubject("Create Account");
            email.setType(MailConst.CreatAccount.toString());
            email.setTo(user.getEmail());
            email.setTemplate("/page/createAccount");
            String baseUrl = WebUtil.getBaseUrl(request);
            email.getVars().put("username", user.getUsername());
            email.getVars().put("baseUrl", baseUrl);
            email.getVars().put("password", passwordRandom);
            sender.send(email);
        }
        user.setPassword(passwordEncoder.encode(form.getPassword().trim()));
        user.setGender(form.getGender());
        user.setFullname(!StringUtils.isEmpty(form.getFullname()) ? form.getFullname().trim() : StringUtils.EMPTY);
        user.setEnabled(true);
        user.setDeleted(false);
        user.setStatus(true);
        user.setPhoneNumber(form.getPhoneNumber());
        user.setAddress(form.getAddress());
        user = userRepository.save(user);
        RoleEntity role;
        if(form.getRoleType() != null){
            role = roleRepository.findByRoleType(form.getRoleType());
        }else{
            role = roleRepository.findByRoleType(RoleType.ROLE_USER);
        }
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
            email.setFrom("bookstore@gmail.com");
            email.setSubject("Reset Password");
            email.setTo(user.getEmail());
            email.setTemplate("/page/reset-password");
            email.setType(MailConst.ResetPassword.toString());
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

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAllByDeleted(false, pageable);
    }

    @Override
    public Boolean deleteAllById(List<Long> ids) {
        ids.forEach(id->userRepository.updateDeletedById(id));
        return true;
    }

    @Override
    public Boolean addOrder(List<BookCart> cart, boolean paypal, HttpServletRequest request) {
        UserEntity currentUser = userRepository.findByUsernameAndDeleted(SecurityUtil.getCurrentUser().getUsername(), false);
        OrderEntity order = new OrderEntity();
        order.setStatus(0);
        order.setCustomer(currentUser);
        order = orderReponsitory.save(order);
        PaymentEntity payment = new PaymentEntity();
        Email email = new Email();
        if(!paypal){
            payment.setName("COD");
            payment.setStatus(0);
            email.getVars().put("payment", "COD");
        }else {
            payment.setName("PayPal");
            payment.setStatus(1);
            email.getVars().put("payment", "PayPal");
        }
        payment.setOrder(order);
        paymentReponsitory.save(payment);
        Long total = new Long(0);
        for (BookCart od: cart) {
            BookEntity book = bookReponsitory.getByIdAndDeleted(od.getBook().getId(), false);
            OrderDetailEntity ot = new OrderDetailEntity();
            OrderDetailID id = new OrderDetailID();
            id.setBookId(book.getId());
            id.setOrderId(order.getId());
            ot.setId(id);
            ot.setQuantity(od.getQuantity());
            if(book.getDiscount() != null && book.getDiscount() != 0){
                ot.setFinalPrice(book.getPrice() - (book.getDiscount() * book.getPrice()/100));
            }else {
                ot.setFinalPrice(book.getPrice());
            }
            book.setQuantity(book.getQuantity() - od.getQuantity());
            order.getOrderDetails().add(ot);
            bookReponsitory.save(book);
            if(book.getDiscount() != null && book.getDiscount() != 0){
                total += (ot.getQuantity() * (book.getPrice() - (book.getPrice() * book.getDiscount()/100)));
            }else {
                total += (ot.getQuantity() * book.getPrice());
            }
        }
        order.setBuyDay(new Date());
        order = orderReponsitory.save(order);
        email.setFrom("bookstore@gmail.com");
        email.setSubject("Confirm Order");
        email.setTo(currentUser.getEmail());
        email.setType(MailConst.ConfirmOrder.toString());
        String orderUrl = WebUtil.getBaseUrl(request) + "/order/" + order.getId();
        email.getVars().put("orderUrl", orderUrl);
        email.getVars().put("total", total);
        email.setTemplate("/page/confirmOrder");
        sender.send(email);
        return true;
    }

    @Override
    public boolean updateProfile(UserEntity userEntity, Profile form) {
        userEntity.setAddress(form.getAddress());
        userEntity.setPhoneNumber(form.getPhoneNumber());
        userEntity.setFullname(form.getFullName());
        if(StringUtils.isNotBlank(form.getPassword())){
            userEntity.setPassword(passwordEncoder.encode(form.getPassword().trim()));
        }
        userRepository.save(userEntity);
        return true;
    }
}
