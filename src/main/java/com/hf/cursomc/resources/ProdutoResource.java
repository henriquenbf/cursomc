package com.hf.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hf.cursomc.domain.Produto;
import com.hf.cursomc.dto.ProdutoDTO;
import com.hf.cursomc.resources.utils.ParamsConverter;
import com.hf.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Integer id) {
        Produto produto = produtoService.find(id);
        return ResponseEntity.ok(produto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(name = "nome", defaultValue = "") String nome,
            @RequestParam(name = "categorias", defaultValue = "") String categorias,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction) {

        List<Integer> idsCategorias = ParamsConverter.convertIdsList(categorias);
        nome = ParamsConverter.decodeString(nome);

        Page<Produto> produtosList = produtoService.search(nome, idsCategorias, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDtos = produtosList.map(produto -> new ProdutoDTO(produto));
        return ResponseEntity.ok().body(listDtos);
    }

}
