package com.code.salesappbackend.controllers;

import com.code.salesappbackend.services.interfaces.EmailService;
import com.code.salesappbackend.utils.EmailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestSendMail {
    private final EmailService emailService;


    @PostMapping
    public String sendMail(@RequestBody EmailDetails emailDetails) {
        return emailService.sendSimpleMail(emailDetails);
    }

    @PostMapping("/attach-file")
    public String sendMailAttachFile(@ModelAttribute EmailDetails emailDetails) throws Exception {
        return emailService.sendMailWithAttachment(emailDetails);
    }

    @PostMapping("/mail-html")
    public String sendMailHtml(@RequestBody EmailDetails emailDetails) throws Exception {
        emailService.sendHtmlMail(emailDetails);
        return "send mail successfully";
    }

}
