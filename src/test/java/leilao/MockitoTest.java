package leilao;

import br.com.leilao.dao.LeilaoDao;
import br.com.leilao.model.Leilao;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class MockitoTest {

    @Test
    @DisplayName("Verificar se lista mockada está vazia")
    void Hello(){
        LeilaoDao mock = Mockito.mock(LeilaoDao.class);
        List<Leilao> listLeilao =  mock.buscarTodos();

        Assert.assertTrue(listLeilao.isEmpty());
    }


}
