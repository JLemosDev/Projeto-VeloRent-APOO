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

        // US-014 CN01: rejeitar se o veículo já estiver alocado (indisponível) no período
        if (!disponivel && temConflitoDeAlocacao(veiculoId, 0, dataInicio, dataFim)) {
            throw new IllegalStateException("Conflito de período: veículo já alocado nesta data.");
        }

        return repository.criar(veiculo, dataInicio, dataFim, disponivel);
    }

    public Disponibilidade editar(int id, int veiculoId, LocalDate dataInicio, LocalDate dataFim, boolean disponivel) {
        Disponibilidade disp = buscarPorId(id);
        Veiculo veiculo = veiculoService.buscarPorId(veiculoId);
        validarPeriodo(dataInicio, dataFim);

        // US-015 CN01: rejeitar se o novo período conflitar com outra alocação (excluindo o próprio registro)
        if (!disponivel && temConflitoDeAlocacao(veiculoId, id, dataInicio, dataFim)) {
            throw new IllegalStateException("Conflito de período: veículo já alocado nesta data.");
        }

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

    /**
     * Verifica se já existe outro registro de INDISPONIBILIDADE para o veículo
     * que se sobreponha ao período informado (excluindo o próprio registro na edição).
     */
    private boolean temConflitoDeAlocacao(int veiculoId, int idIgnorado, LocalDate inicio, LocalDate fim) {
        return repository.listarPorVeiculo(veiculoId).stream()
                .filter(d -> d.getId() != idIgnorado)
                .filter(d -> !d.isDisponivel())
                .anyMatch(d -> d.getDataInicio().compareTo(fim) <= 0
                            && d.getDataFim().compareTo(inicio) >= 0);
    }
}
