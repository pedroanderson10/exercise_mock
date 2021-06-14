package br.com.leilao.service;

import java.time.LocalDate;

import br.com.leilao.dao.PagamentoDao;
import br.com.leilao.model.Lance;
import br.com.leilao.model.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeradorDePagamento {

	@Autowired
	private PagamentoDao pagamentos;

	public void gerarPagamento(Lance lanceVencedor) {
		LocalDate vencimento = LocalDate.now().plusDays(1);
		Pagamento pagamento = new Pagamento(lanceVencedor, vencimento);
		this.pagamentos.salvar(pagamento);
	}

}
