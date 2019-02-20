package com.framgia.bookStore.controller;

import com.framgia.bookStore.constants.PaypalPaymentIntent;
import com.framgia.bookStore.constants.PaypalPaymentMethod;
import com.framgia.bookStore.form.BookCart;
import com.framgia.bookStore.service.PaypalService;
import com.framgia.bookStore.util.PaypalUtils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pay")
public class PaymentController extends BaseController{
    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";

    private static final Logger LOGGER = LogManager.getLogger(PaymentController.class);

    @Autowired
    private PaypalService paypalService;

    @GetMapping("/paypal")
    public String pay(HttpServletRequest request, HttpSession session){
        Long money = (long) session.getAttribute("totalPrice");
        if (money == 0){
            return "redirect:/cart";
        }
        double price = money.doubleValue()/25000;

        String cancelUrl = PaypalUtils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = PaypalUtils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        try {
            Payment payment = paypalService.createPayment(
                price,
                "USD",
                PaypalPaymentMethod.paypal,
                PaypalPaymentIntent.sale,
                "payment description",
                cancelUrl,
                successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            LOGGER.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay(){
        return "cancel";
    }

    @GetMapping("/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpSession session){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                List<BookCart> cart = (List<BookCart>) session.getAttribute("cart");
                if(userService.addOrder(cart, true)){
                    session.setAttribute("totalPrice", new Long(0));
                    session.setAttribute("cart", null);
                    return "redirect:/cart";
                }
                return "redirect:/";
            }
        } catch (PayPalRESTException e) {
            LOGGER.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/COD")
    public String codPay(HttpSession session){
        Long money = (long) session.getAttribute("totalPrice");
        if (money == 0){
            return "redirect:/cart";
        }
        List<BookCart> cart = (List<BookCart>) session.getAttribute("cart");
        if(userService.addOrder(cart, false)){
            session.setAttribute("totalPrice", new Long(0));
            session.setAttribute("cart", null);
            return "redirect:/cart";
        }
        return "redirect:/";
    }
}
