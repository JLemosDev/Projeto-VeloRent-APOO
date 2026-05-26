package repository;

import model.Disponibilidade;
import model.Veiculo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DisponibilidadeRepository {
    private final List<Disponibilidade> registros = new ArrayList<>();
    private int proximoId = 1;

    public Disponibilidade criar(Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim, boolean disponivel) {
        Disponibilidade d = new Disponibilidade(proximoId++, veiculo, dataInicio, dataFim, disponivel);
        registros.add(d);
        return d;
    }

    public Disponibilidade salvar(Disponibilidade disponibilidade) {
        for (int i = 0; i < registros.size(); i++) {
            if (registros.get(i).getId() == disponibilidade.getId()) {
                registros.set(i, disponibilidade);
                return disponibilidade;
            }
        }
        registros.add(disponibilidade);
        return disponibilidade;
    }

    public Optional<Disponibilidade> buscarPorId(int id) {
        return registros.stream().filter(d -> d.getId() == id).findFirst();
    }

    public List<Disponibilidade> listarTodos() {
        return new ArrayList<>(registros);
    }

    public List<Disponibilidade> listarPorVeiculo(int veiculoId) {
        return registros.stream()
                .filter(d -> d.getVeiculo().getId() == veiculoId)
                .collect(Collectors.toList());
    }

    /**
     * Verifica se um veículo está disponível no período solicitado.
     * Retorna true se não houver nenhum registro de INdisponibilidade que conflite.
     */
    public boolean estaDisponivel(int veiculoId, LocalDate inicio, LocalDate fim) {
        return registros.stream()
                .filter(d -> d.getVeiculo().getId() == veiculoId && !d.isDisponivel())
                .noneMatch(d -> periodoConflita(d.getDataInicio(), d.getDataFim(), inicio, fim));
    }

    private boolean periodoConflita(LocalDate ini1, LocalDate fim1, LocalDate ini2, LocalDate fim2) {
        return !ini1.isAfter(fim2) && !fim1.isBefore(ini2);
    }

    public boolean remover(int id) {
        return registros.removeIf(d -> d.getId() == id);
    }
}
