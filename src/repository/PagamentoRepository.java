package repository;

import model.FormaPagamento;
import model.Orcamento;
import model.Pagamento;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PagamentoRepository {
    private final List<Pagamento> pagamentos = new ArrayList<>();
    private int proximoId = 1;

    public Pagamento criar(Orcamento orcamento, double valorPago, LocalDate dataPagamento, FormaPagamento formaPagamento) {
        Pagamento p = new Pagamento(proximoId++, orcamento, valorPago, dataPagamento, formaPagamento);
        pagamentos.add(p);
        return p;
    }

    public Pagamento salvar(Pagamento pagamento) {
        for (int i = 0; i < pagamentos.size(); i++) {
            if (pagamentos.get(i).getId() == pagamento.getId()) {
                pagamentos.set(i, pagamento);
                return pagamento;
            }
        }
        pagamentos.add(pagamento);
        return pagamento;
    }

    public Optional<Pagamento> buscarPorId(int id) {
        return pagamentos.stream().filter(p -> p.getId() == id).findFirst();
    }

    public List<Pagamento> listarTodos() {
        return new ArrayList<>(pagamentos);
    }

    public boolean remover(int id) {
        return pagamentos.removeIf(p -> p.getId() == id);
    }
}
