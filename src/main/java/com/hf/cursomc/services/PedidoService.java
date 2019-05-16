package com.hf.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hf.cursomc.domain.Pedido;
import com.hf.cursomc.exceptions.ObjectNotFoundException;
import com.hf.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;

    public Pedido find(Integer id) {
        Optional<Pedido> categoriaOtp = pedidoRepo.findById(id);
        return categoriaOtp.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto com ID = %s não encontrado. Tipo = %s", id, Pedido.class.getName())));
    }

}
