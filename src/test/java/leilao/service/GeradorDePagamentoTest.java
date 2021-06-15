package leilao.service;

import br.com.leilao.dao.PagamentoDao;
import br.com.leilao.model.Lance;
import br.com.leilao.model.Leilao;
import br.com.leilao.model.Pagamento;
import br.com.leilao.model.Usuario;
import br.com.leilao.service.GeradorDePagamento;
import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations.*;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GeradorDePagamentoTest {

    private GeradorDePagamento geradorDePagamento;

    @Mock
    private PagamentoDao pagamentoDao;

    @Captor //Capturar um objeto que é criado na classe de teste (que foi passado por um método de um mock)
    private ArgumentCaptor<Pagamento> captorPagamento;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.initMocks(this);
        this.geradorDePagamento = new GeradorDePagamento(pagamentoDao);
    }

    @Test
    @DisplayName("Gerar pagamento para vencedor do Leilão")
    void gerarPagamentoParaVencedorLeilao(){
        Leilao leilao = leilao();
        Lance lanceVencedor = leilao.getLanceVencedor();
        geradorDePagamento.gerarPagamento(lanceVencedor);

        Mockito.verify(pagamentoDao).salvar(captorPagamento.capture());
        Pagamento pagamento = captorPagamento.getValue();

        //Usuario userTeste = new Usuario("User Teste");
        assertEquals(LocalDate.now().plusDays(1), pagamento.getVencimento() );
        assertEquals(lanceVencedor.getValor(), pagamento.getValor());
        assertFalse(pagamento.getPago());
        assertEquals(lanceVencedor.getUsuario(), pagamento.getUsuario());
        assertEquals(leilao, pagamento.getLeilao());
    }

    private Leilao leilao(){
        Leilao leilao = new Leilao("Celular", new BigDecimal("500"), new Usuario("Pedro"));
        Lance primeiroLance = new Lance(new Usuario("Lana"), new BigDecimal("600"));
        leilao.propoe(primeiroLance);
        leilao.setLanceVencedor(primeiroLance);

        return leilao;
    }

}
