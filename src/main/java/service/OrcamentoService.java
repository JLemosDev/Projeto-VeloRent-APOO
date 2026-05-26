package service;

import model.Cliente;
import model.Orcamento;
import model.Veiculo;
import repository.OrcamentoRepository;
import java.time.LocalDate;
import java.util.List;

public class OrcamentoService {
    private final OrcamentoRepository repository;
    private final ClienteService clienteService;
    private final VeiculoService veiculoService;
    private final DisponibilidadeService disponibilidadeService;

    public OrcamentoService(OrcamentoRepository repository,
                            ClienteService clienteService,
                            VeiculoService veiculoService,
                            DisponibilidadeService disponibilidadeService) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.veiculoService = veiculoService;
        this.disponibilidadeService = disponibilidadeService;
    }

    public Orcamento cadastrar(int clienteId, int veiculoId, LocalDate dataInicio, LocalDate dataFim) {
        Cliente cliente = clienteService.buscarPorId(clienteId);
        Veiculo veiculo = veiculoService.buscarPorId(veiculoId);
        validarPeriodo(dataInicio, dataFim);

        if (!disponibilidadeService.verificarDisponibilidade(veiculoId, dataInicio, dataFim)) {
            throw new IllegalStateException("O veículo não está disponível no período informado.");
        }

        return repository.criar(cliente, veiculo, dataInicio, dataFim);
    }

    public Orcamento editar(int id, int clienteId, int veiculoId, LocalDate dataInicio, LocalDate dataFim) {
        Orcamento orc = buscarPorId(id);
        Cliente cliente = clienteService.buscarPorId(clienteId);
        Veiculo veiculo = veiculoService.buscarPorId(veiculoId);
        validarPeriodo(dataInicio, dataFim);

        orc.setCliente(cliente);
        orc.setVeiculo(veiculo);
        orc.setDataInicio(dataInicio);
        orc.setDataFim(dataFim);
        return repository.salvar(orc);
    }

    public boolean excluir(int id) {
        buscarPorId(id);
        return repository.remover(id);
    }

    public List<Orcamento> listarTodos() {
        return repository.listarTodos();
    }

    public List<Orcamento> listarPorCliente(int clienteId) {
        return repository.listarPorCliente(clienteId);
    }

    public Orcamento buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Orçamento não encontrado com ID: " + id));
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null)
            throw new IllegalArgumentException("As datas são obrigatórias.");
        if (!inicio.isBefore(fim))
            throw new IllegalArgumentException("A data de início deve ser anterior à data de fim.");
    }
}
