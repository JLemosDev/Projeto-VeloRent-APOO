package service;

import model.Categoria;
import model.Veiculo;
import repository.VeiculoRepository;
import java.util.List;
import java.util.Optional;

public class VeiculoService {
    private final VeiculoRepository repository;

    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    public Veiculo cadastrar(String marca, String modelo, String placa, int ano, Categoria categoria, double valorDiaria) {
        validarCampos(marca, modelo, placa, ano, valorDiaria);
        if (repository.buscarPorPlaca(placa).isPresent()) {
            throw new IllegalArgumentException("Já existe um veículo cadastrado com a placa: " + placa);
        }
        return repository.criar(marca, modelo, placa, ano, categoria, valorDiaria);
    }

    public Veiculo editar(int id, String marca, String modelo, String placa, int ano, Categoria categoria, double valorDiaria) {
        Veiculo veiculo = buscarPorId(id);
        validarCampos(marca, modelo, placa, ano, valorDiaria);

        Optional<Veiculo> existente = repository.buscarPorPlaca(placa);
        if (existente.isPresent() && existente.get().getId() != id) {
            throw new IllegalArgumentException("Placa já cadastrada para outro veículo.");
        }

        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setPlaca(placa);
        veiculo.setAno(ano);
        veiculo.setCategoria(categoria);
        veiculo.setValorDiaria(valorDiaria);
        return repository.salvar(veiculo);
    }

    public boolean excluir(int id) {
        buscarPorId(id);
        return repository.remover(id);
    }

    public List<Veiculo> listarTodos() {
        return repository.listarTodos();
    }

    public List<Veiculo> listarPorCategoria(Categoria categoria) {
        return repository.listarPorCategoria(categoria);
    }

    public Veiculo buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado com ID: " + id));
    }

    private void validarCampos(String marca, String modelo, String placa, int ano, double valorDiaria) {
        if (marca == null || marca.trim().isEmpty())
            throw new IllegalArgumentException("Marca é obrigatória.");
        if (modelo == null || modelo.trim().isEmpty())
            throw new IllegalArgumentException("Modelo é obrigatório.");
        if (placa == null || placa.trim().isEmpty())
            throw new IllegalArgumentException("Placa é obrigatória.");
        if (ano < 1900 || ano > 2100)
            throw new IllegalArgumentException("Ano inválido.");
        if (valorDiaria <= 0)
            throw new IllegalArgumentException("Valor da diária deve ser maior que zero.");
    }
}
