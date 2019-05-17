package com.hf.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hf.cursomc.domain.Categoria;
import com.hf.cursomc.domain.Cidade;
import com.hf.cursomc.domain.Cliente;
import com.hf.cursomc.domain.Endereco;
import com.hf.cursomc.domain.Estado;
import com.hf.cursomc.domain.ItemPedido;
import com.hf.cursomc.domain.Pagamento;
import com.hf.cursomc.domain.PagamentoComBoleto;
import com.hf.cursomc.domain.PagamentoComCartao;
import com.hf.cursomc.domain.Pedido;
import com.hf.cursomc.domain.Produto;
import com.hf.cursomc.domain.enums.EstadoPagamento;
import com.hf.cursomc.domain.enums.TipoCliente;
import com.hf.cursomc.repositories.CategoriaRepository;
import com.hf.cursomc.repositories.CidadeRepository;
import com.hf.cursomc.repositories.ClienteRepository;
import com.hf.cursomc.repositories.EnderecoRepository;
import com.hf.cursomc.repositories.EstadoRepository;
import com.hf.cursomc.repositories.ItemPedidoRepository;
import com.hf.cursomc.repositories.PedidoRepository;
import com.hf.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CidadeRepository cidadeRepostory;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Smartphones");
        Categoria cat4 = new Categoria(null, "Eletrodomésticos");
        Categoria cat5 = new Categoria(null, "Brinquedos");
        Categoria cat6 = new Categoria(null, "Bebê");
        Categoria cat7 = new Categoria(null, "Cama, mesa e banho");

        Produto p1 = new Produto(null, "Computador", 2000D);
        Produto p2 = new Produto(null, "Impressora", 800D);
        Produto p3 = new Produto(null, "Mouse", 80D);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().add(p2);

        p1.getCategorias().add(cat1);
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().add(cat1);

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade cid1 = new Cidade(null, "Uberlândia", est1);
        Cidade cid2 = new Cidade(null, "São Paulo", est2);
        Cidade cid3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().add(cid1);
        est2.getCidades().addAll(Arrays.asList(cid2, cid3));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepostory.saveAll(Arrays.asList(cid1, cid2, cid3));

        Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "35484788977", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("4836362245", "4899887523"));

        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "8879854", cli1, cid1);
        Endereco e2 = new Endereco(null, "Av. Matos", "105", "Sala 800", "Centro", "88741447", cli1, cid2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        clienteRepository.save(cli1);

        enderecoRepository.saveAll(Arrays.asList(e1, e2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 09:35"), cli1, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));

        ItemPedido item1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.0);
        ItemPedido item2 = new ItemPedido(ped1, p3, 0.0, 2, 80.0);
        ItemPedido item3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);

        ped1.getItens().addAll(Arrays.asList(item1, item2));

        ped2.getItens().add(item3);

        p1.getItens().add(item1);
        p2.getItens().add(item3);
        p3.getItens().add(item2);

        itemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3));

    }
}
