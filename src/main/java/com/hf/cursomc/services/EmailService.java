package com.hf.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.hf.cursomc.domain.Pedido;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage mailMsg);

}
