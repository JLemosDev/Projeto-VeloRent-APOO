package service;

import model.FormaPagamento;
import model.Orcamento;
import model.Pagamento;
import repository.PagamentoRepository;
import java.time.LocalDate;
import java.util.List;

public class PagamentoService {
    private final PagamentoRepository repository;
    private final OrcamentoService orcamentoService;

    public PagamentoService(PagamentoRepository repository, OrcamentoService orcamentoService) {
        this.repository = repository;
        this.orcamentoService = orcamentoService;
    }

    public Pagamento cadastrar(int orcamentoId, double valorPago, LocalDate dataPagamento, FormaPagamento forma) {
        Orcamento orcamento = orcamentoService.buscarPorId(orcamentoId);
        if (valorPago <= 0)
            throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero.");
        if (dataPagamento == null)
            throw new IllegalArgumentException("Data de pagamento é obrigatória.");

        return repository.criar(orcamento, valorPago, dataPagamento, forma);
    }

    public Pagamento editar(int id, int orcamentoId, double valorPago, LocalDate dataPagamento, FormaPagamento forma) {
        Pagamento pag = buscarPorId(id);
        Orcamento orcamento = orcamentoService.buscarPorId(orcamentoId);

        if (valorPago <= 0)
            throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero.");
        if (dataPagamento == null)
            throw new IllegalArgumentException("Data de pagamento é obrigatória.");

        pag.setOrcamento(orcamento);
        pag.setValorPago(valorPago);
        pag.setDataPagamento(dataPagamento);
        pag.setFormaPagamento(forma);
        return repository.salvar(pag);
    }

    public boolean excluir(int id) {
        buscarPorId(id);
        return repository.remover(id);
    }

    public List<Pagamento> listarTodos() {
        return repository.listarTodos();
    }

    public Pagamento buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado com ID: " + id));
    }
}
