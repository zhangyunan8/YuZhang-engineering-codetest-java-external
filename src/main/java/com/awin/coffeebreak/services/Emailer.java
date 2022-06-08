package com.awin.coffeebreak.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class Emailer {

    private String emailAddress_To;
    private String emailAddress_From;
    private String emailAddress_CC;
    private String subject;
    private String msg_body;
    @Autowired
    private JavaMailSender javaMailSender;
    public Emailer(){}
    public boolean Send(String emailAddress_To, String emailAddress_From, String emailAddress_CC, String subject, String msg_body){
        // do something to send email here
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailAddress_To, "to_2@gmail.com", "to_3@yahoo.com");
        msg.setCc(emailAddress_CC);
        msg.setFrom(emailAddress_From);
        msg.setSubject(subject);
        msg.setText(msg_body);

        javaMailSender.send(msg);
        return true;
    }
}
