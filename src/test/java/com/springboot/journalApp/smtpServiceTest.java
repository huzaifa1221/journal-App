package com.springboot.journalApp;

import com.springboot.journalApp.service.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class smtpServiceTest
{

    @Autowired private EmailService smtpservice;

    @Test
    @Disabled
    public void sendMailTest()
    {
        smtpservice.sendMail("<ENTER MAIL ID>",
                "this is the subject matter of the mail",
                "this is the body of the mail, hi huzaifa");
    }
}
