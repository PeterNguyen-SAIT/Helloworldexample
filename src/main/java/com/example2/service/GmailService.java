package com.example2.service;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GmailService {

    public static void sendMail1(String email, String subject,
                                String template, HashMap<String, String> tags) {
        try {
            // read in template in to a string
            BufferedReader br = new BufferedReader(new FileReader(new File(template)));
            String line = br.readLine();
            String body = "";
            while(line != null) {
                body += line;
                line = br.readLine();
            }

            // replace all heart tags with values
            for(String tag : tags.keySet()) {
                body = body.replace("%" + tag + "%", tags.get(tag));

            }

            // send email
            sendMail2(email, subject, body, true);

        } catch (IOException | MessagingException | NamingException | javax.mail.MessagingException ex) {
            Logger.getLogger(GmailService.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public static void sendMail2(String to, String subject, String body, boolean bodyIsHTML) throws MessagingException, NamingException, javax.mail.MessagingException {
        //Context env = (Context)new InitialContext().lookup("java:comp/env");
        String username = "Admin Gmail username";//(String)env.lookup("webmail-username");
        String password = "Admin Gmail password";//(String)env.lookup("webmail-password");

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.port", "465");
        //props.put("mail.smtp.starttls.enable","true" );
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        //props.put("mail.smtp.connectiontimeout", "1000");
        //props.put("mail.smtp.timeout", "1000");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        // create a message
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }

        // address the message
        //Address fromAddress = new InternetAddress(username);
        Address toAddress = new InternetAddress(to);
        //message.setFrom("cprg352@sait.ca");
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // send the message
        Transport transport = session.getTransport();
        transport.connect(username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
