package com.personal.diplom.Servise;

import com.personal.diplom.api.response.ResponseResult;
import com.personal.diplom.model.User;
import com.personal.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.swing.text.html.Option;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

@Service
public class DefaultEmailService implements EmailService {
    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }
    public static String generateString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    public ResponseResult checkUserAndMail(String email){
        ResponseResult responseResult = new ResponseResult();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()){
            responseResult.setResult(false);
            return responseResult;
        }
        User user = optionalUser.get();
        user.setCode(generateString(16));
        userRepository.save(user);
        System.out.println("user "+ user.getName());
        try {
            sendSimpleEmail(user.getEmail(), "AHTUNG!!!", "ссылка на восстановление пароля /login/change-password/"+user.getCode());
            responseResult.setResult(true);
        } catch (MailException mailException) {
            responseResult.setResult(false);
            return responseResult;
        }
        return  responseResult;
    }
  /*  @Override
    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
        messageHelper.addAttachment("Purchase Order", file);
        emailSender.send(mimeMessage);
    }*/

}
