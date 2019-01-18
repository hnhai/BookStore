package com.framgia.bookStore.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class MyErrorController extends BaseController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model, Locale locale) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            switch (statusCode) {
                case 404:
                    model.addAttribute("message", messageSource.getMessage("error-page.message-404", null, locale));
                    model.addAttribute("code", statusCode);
                    model.addAttribute("background", "#308ee0");
                    break;
                case 500:
                    model.addAttribute("message",  messageSource.getMessage("error-page.message-500", null, locale));
                    model.addAttribute("code", statusCode);
                    model.addAttribute("background", "#8862e0");
                    break;
                default:
                    model.addAttribute("message",  messageSource.getMessage("error-page.default", null, locale));
                    model.addAttribute("code", "");
                    model.addAttribute("background", "#308ee0");
                    break;
            }
        }
        return "/page/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
