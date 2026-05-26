package repository;

import model.Categoria;
import model.Veiculo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VeiculoRepository {
    private final List<Veiculo> veiculos = new ArrayList<>();
    private int proximoId = 1;

    public Veiculo criar(String marca, String modelo, String placa, int ano, Categoria categoria, double valorDiaria) {
        Veiculo v = new Veiculo(proximoId++, marca, modelo, placa, ano, categoria, valorDiaria);
        veiculos.add(v);
        return v;
    }

    public Veiculo salvar(Veiculo veiculo) {
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getId() == veiculo.getId()) {
                veiculos.set(i, veiculo);
                return veiculo;
            }
        }
        veiculos.add(veiculo);
        return veiculo;
    }

    public Optional<Veiculo> buscarPorId(int id) {
        return veiculos.stream().filter(v -> v.getId() == id).findFirst();
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return veiculos.stream().filter(v -> v.getPlaca().equalsIgnoreCase(placa)).findFirst();
    }

    public List<Veiculo> listarTodos() {
        return new ArrayList<>(veiculos);
    }

    public List<Veiculo> listarPorCategoria(Categoria categoria) {
        return veiculos.stream()
                .filter(v -> v.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    public boolean remover(int id) {
        return veiculos.removeIf(v -> v.getId() == id);
    }
}
