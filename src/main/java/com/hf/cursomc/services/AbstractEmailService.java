package com.hf.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.hf.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

    @Value("${mail.default.sender}")
    private String mailSender;

    @Override
    public void sendOrderConfirmationEmail(Pedido pedido) {
        SimpleMailMessage mailMsg = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(mailMsg);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setTo(pedido.getCliente().getEmail());
        mailMsg.setFrom(mailSender);
        mailMsg.setSubject("Confirmação do pedido " + pedido.getId());
        mailMsg.setSentDate(new Date(System.currentTimeMillis()));
        mailMsg.setText(pedido.toString());
        return mailMsg;
    }

}
