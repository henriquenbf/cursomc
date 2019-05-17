package com.hf.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hf.cursomc.domain.Cliente;
import com.hf.cursomc.dto.ClienteDTO;
import com.hf.cursomc.exceptions.DataIntegrityException;
import com.hf.cursomc.exceptions.ObjectNotFoundException;
import com.hf.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente find(Integer id) {
        Optional<Cliente> ClienteOtp = repository.findById(id);
        return ClienteOtp.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto com ID = %s não encontrado. Tipo = %s", id, Cliente.class.getName())));
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        return repository.save(obj);
    }

    public Cliente update(Cliente obj) {
        Cliente entityDb = find(obj.getId());
        updateData(entityDb, obj);
        return repository.save(entityDb);
    }

    private void updateData(Cliente entityDb, Cliente obj) {
        entityDb.setNome(obj.getNome());
        entityDb.setEmail(obj.getEmail());
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cliente que possui Pedidos.");
        }
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO dto) {
        return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
    }

}
