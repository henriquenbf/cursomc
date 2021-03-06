package com.hf.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hf.cursomc.domain.Categoria;
import com.hf.cursomc.dto.CategoriaDTO;
import com.hf.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        Categoria entity = service.find(id);
        return ResponseEntity.ok(entity);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO dto) {
        Categoria entity = service.insert(service.fromDTO(dto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO dto, @PathVariable Integer id) {
        Categoria entity = service.fromDTO(dto);
        entity.setId(id);
        entity = service.update(entity);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> list = service.findAll();
        List<CategoriaDTO> listDtos = list.stream().map(categ -> new CategoriaDTO(categ)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDtos);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction) {

        Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> listDtos = list.map(categ -> new CategoriaDTO(categ));
        return ResponseEntity.ok().body(listDtos);
    }

}
