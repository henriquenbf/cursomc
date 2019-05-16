package com.hf.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hf.cursomc.domain.Cliente;
import com.hf.cursomc.exceptions.ObjectNotFoundException;
import com.hf.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository ClienteRepo;

    public Cliente find(Integer id) {
        Optional<Cliente> ClienteOtp = ClienteRepo.findById(id);
        return ClienteOtp.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto com ID = %s não encontrado. Tipo = %s", id, Cliente.class.getName())));
    }

}
