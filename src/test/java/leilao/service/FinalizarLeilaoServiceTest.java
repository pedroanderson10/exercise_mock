package leilao.service;


import static org.junit.jupiter.api.Assertions.*;

import br.com.leilao.dao.LeilaoDao;
import br.com.leilao.model.Lance;
import br.com.leilao.model.Leilao;
import br.com.leilao.model.Usuario;
import br.com.leilao.service.EnviadorDeEmails;
import br.com.leilao.service.FinalizarLeilaoService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FinalizarLeilaoServiceTest {

    private FinalizarLeilaoService finalizarLeilaoService;

    @Mock //Mockito agora sabe que deve criar um mock dessa clase, porém isso não é feito automaticamente
    private LeilaoDao leilaoDao;

    @Mock
    private EnviadorDeEmails enviadorDeEmails;

    @BeforeEach //Indicar ao mockito, para antes de cada método de teste, ler todos atributos da classe de teste (com @Mock)
    public void beforeEach(){
        MockitoAnnotations.initMocks(this); //Ler anotações do mockito
        this.finalizarLeilaoService = new FinalizarLeilaoService(leilaoDao, enviadorDeEmails);
    }

    @Test
    @DisplayName("Finalizar um leilão")
    void finalizarUmLeilao(){
        List<Leilao> listLeiloes = leiloes();

        //Manipular o mockito para receber a lista criada, após o método especificado ser chamado
        Mockito.when(leilaoDao.buscarLeiloesExpirados()).thenReturn(listLeiloes);

        finalizarLeilaoService.finalizarLeiloesExpirados();

        Leilao leilao = listLeiloes.get(0);
        assertTrue(leilao.isFechado());
        assertEquals(new BigDecimal("900"), leilao.getLanceVencedor().getValor());

        //Verificar se um determinado método de um mock foi executado
        Mockito.verify(leilaoDao).salvar(leilao);

    }

    @Test
    @DisplayName("Enviar email para o vencedor do leilão")
    void enviarEmailParaVencedorDoLeilao(){
        List<Leilao> listLeiloes = leiloes();

        //Manipular o mockito para receber a lista criada, após o método especificado ser chamado
        Mockito.when(leilaoDao.buscarLeiloesExpirados()).thenReturn(listLeiloes);

        finalizarLeilaoService.finalizarLeiloesExpirados();

        Leilao leilao = listLeiloes.get(0);
        Lance lanceVencedor = leilao.getLanceVencedor();

        //Verificar se um determinado método de um mock foi executado
        Mockito.verify(enviadorDeEmails).enviarEmailVencedorLeilao(lanceVencedor);

    }

    private List<Leilao> leiloes(){
        List<Leilao> list = new ArrayList<>();

        Leilao leilao = new Leilao("Celular", new BigDecimal("500"), new Usuario("Pedro"));

        Lance primeiroLance = new Lance(new Usuario("Lana"), new BigDecimal("600"));
        Lance segundoLance = new Lance(new Usuario("Andrew"), new BigDecimal("900"));

        leilao.propoe(primeiroLance);
        leilao.propoe(segundoLance);

        list.add(leilao);

        return list;
    }

}
