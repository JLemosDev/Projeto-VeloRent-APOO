package repository;

import model.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepository {
    private final List<Cliente> clientes = new ArrayList<>();
    private int proximoId = 1;

    public Cliente salvar(Cliente cliente) {
        if (buscarPorId(cliente.getId()).isPresent()) {
            // Atualizar existente
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getId() == cliente.getId()) {
                    clientes.set(i, cliente);
                    return cliente;
                }
            }
        }
        clientes.add(cliente);
        return cliente;
    }

    public Cliente criar(String nome, String cpf, String telefone, String endereco, String email) {
        Cliente c = new Cliente(proximoId++, nome, cpf, telefone, endereco, email);
        clientes.add(c);
        return c;
    }

    public Optional<Cliente> buscarPorId(int id) {
        return clientes.stream().filter(c -> c.getId() == id).findFirst();
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clientes.stream().filter(c -> c.getCpf().equals(cpf)).findFirst();
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }

    public boolean remover(int id) {
        return clientes.removeIf(c -> c.getId() == id);
    }

    public int getProximoId() { return proximoId; }
}
