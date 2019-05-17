package com.hf.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hf.cursomc.domain.Categoria;
import com.hf.cursomc.dto.CategoriaDTO;
import com.hf.cursomc.exceptions.DataIntegrityException;
import com.hf.cursomc.exceptions.ObjectNotFoundException;
import com.hf.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepo;

    public Categoria find(Integer id) {
        Optional<Categoria> categoriaOtp = categoriaRepo.findById(id);
        return categoriaOtp.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto com ID = %s não encontrado. Tipo = %s", id, Categoria.class.getName())));
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return categoriaRepo.save(obj);
    }

    public Categoria update(Categoria obj) {
        find(obj.getId());
        return categoriaRepo.save(obj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            categoriaRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui Produtos.");
        }
    }

    public List<Categoria> findAll() {
        return categoriaRepo.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return categoriaRepo.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO dto) {
        return new Categoria(dto.getId(), dto.getNome());
    }

}
