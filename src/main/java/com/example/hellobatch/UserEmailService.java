package com.example.hellobatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserEmailService {

    private final JavaMailSender mailSender;

    public UserEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendProcessedMail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("[처리 완료] " + user.getName() + "님");
        message.setText("안녕하세요 " + user.getName() + "님,\n\n요청하신 정보가 정상적으로 처리되었습니다.");

        try {
            mailSender.send(message);
            System.out.println("📧 이메일 전송 완료: " + user.getEmail());
        } catch (Exception e) {
            System.out.println("⚠ 이메일 전송 실패: " + user.getEmail());
            e.printStackTrace();
        }
    }

}
