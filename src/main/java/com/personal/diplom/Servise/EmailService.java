package com.personal.diplom.Servise;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public interface EmailService {
    void sendSimpleEmail(final String toAddress, final String subject, final String message);
  //  void sendEmailWithAttachment(final String toAddress, final String subject, final String message, final String attachment) throws MessagingException, FileNotFoundException;

}
