package com.hf.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.hf.cursomc.domain.Cliente;
import com.hf.cursomc.domain.enums.TipoCliente;
import com.hf.cursomc.dto.ClienteNewDTO;
import com.hf.cursomc.repositories.ClienteRepository;
import com.hf.cursomc.resources.exception.FieldMessage;
import com.hf.cursomc.services.validation.utils.DocumentUtil;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (TipoCliente.PESSOAFISICA.getCod() == dto.getTipo() && !DocumentUtil.isValidCPF(dto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (TipoCliente.PESSOAJURIDICA.getCod() == dto.getTipo() && !DocumentUtil.isValidCNPJ(dto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Cliente cli = repo.findByEmail(dto.getEmail());
        if (cli != null) {
            list.add(new FieldMessage("email", "Email já cadastrado"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}