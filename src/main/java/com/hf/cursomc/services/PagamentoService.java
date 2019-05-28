package com.hf.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hf.cursomc.domain.Pagamento;
import com.hf.cursomc.repositories.PagamentoRepository;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    public Pagamento insert(Pagamento obj) {
//        obj.setId(null);
        return repository.save(obj);
    }

}
