package service;

import model.Disponibilidade;
import model.Veiculo;
import repository.DisponibilidadeRepository;
import java.time.LocalDate;
import java.util.List;

public class DisponibilidadeService {
    private final DisponibilidadeRepository repository;
    private final VeiculoService veiculoService;

    public DisponibilidadeService(DisponibilidadeRepository repository, VeiculoService veiculoService) {
        this.repository = repository;
        this.veiculoService = veiculoService;
    }

    public Disponibilidade cadastrar(int veiculoId, LocalDate dataInicio, LocalDate dataFim, boolean disponivel) {
        Veiculo veiculo = veiculoService.buscarPorId(veiculoId);
        validarPeriodo(dataInicio, dataFim);
        return repository.criar(veiculo, dataInicio, dataFim, disponivel);
    }

    public Disponibilidade editar(int id, int veiculoId, LocalDate dataInicio, LocalDate dataFim, boolean disponivel) {
        Disponibilidade disp = buscarPorId(id);
        Veiculo veiculo = veiculoService.buscarPorId(veiculoId);
        validarPeriodo(dataInicio, dataFim);

        disp.setVeiculo(veiculo);
        disp.setDataInicio(dataInicio);
        disp.setDataFim(dataFim);
        disp.setDisponivel(disponivel);
        return repository.salvar(disp);
    }

    public boolean excluir(int id) {
        buscarPorId(id);
        return repository.remover(id);
    }

    public List<Disponibilidade> listarTodos() {
        return repository.listarTodos();
    }

    public List<Disponibilidade> listarPorVeiculo(int veiculoId) {
        return repository.listarPorVeiculo(veiculoId);
    }

    public boolean verificarDisponibilidade(int veiculoId, LocalDate inicio, LocalDate fim) {
        return repository.estaDisponivel(veiculoId, inicio, fim);
    }

    public Disponibilidade buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro de disponibilidade não encontrado com ID: " + id));
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null)
            throw new IllegalArgumentException("As datas são obrigatórias.");
        if (!inicio.isBefore(fim))
            throw new IllegalArgumentException("A data de início deve ser anterior à data de fim.");
    }
}
