package repository;

import model.Cliente;
import model.Orcamento;
import model.Veiculo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrcamentoRepository {
    private final List<Orcamento> orcamentos = new ArrayList<>();
    private int proximoId = 1;

    public Orcamento criar(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) {
        Orcamento o = new Orcamento(proximoId++, cliente, veiculo, dataInicio, dataFim);
        orcamentos.add(o);
        return o;
    }

    public Orcamento salvar(Orcamento orcamento) {
        for (int i = 0; i < orcamentos.size(); i++) {
            if (orcamentos.get(i).getId() == orcamento.getId()) {
                orcamentos.set(i, orcamento);
                return orcamento;
            }
        }
        orcamentos.add(orcamento);
        return orcamento;
    }

    public Optional<Orcamento> buscarPorId(int id) {
        return orcamentos.stream().filter(o -> o.getId() == id).findFirst();
    }

    public List<Orcamento> listarTodos() {
        return new ArrayList<>(orcamentos);
    }

    public List<Orcamento> listarPorCliente(int clienteId) {
        return orcamentos.stream()
                .filter(o -> o.getCliente().getId() == clienteId)
                .collect(Collectors.toList());
    }

    public boolean remover(int id) {
        return orcamentos.removeIf(o -> o.getId() == id);
    }
}
