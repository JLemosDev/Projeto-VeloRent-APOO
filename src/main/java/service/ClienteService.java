package service;

import model.Cliente;
import repository.ClienteRepository;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente cadastrar(String nome, String cpf, String telefone, String endereco, String email) {
        validarCampos(nome, cpf, telefone, email);
        if (repository.buscarPorCpf(cpf).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com o CPF: " + cpf);
        }
        return repository.criar(nome, cpf, telefone, endereco, email);
    }

    public Cliente editar(int id, String nome, String cpf, String telefone, String endereco, String email) {
        Cliente cliente = buscarPorId(id);
        validarCampos(nome, cpf, telefone, email);

        // Verifica se o novo CPF já pertence a outro cliente
        Optional<Cliente> existente = repository.buscarPorCpf(cpf);
        if (existente.isPresent() && existente.get().getId() != id) {
            throw new IllegalArgumentException("CPF já cadastrado para outro cliente.");
        }

        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        cliente.setEmail(email);
        return repository.salvar(cliente);
    }

    public boolean excluir(int id) {
        buscarPorId(id); // valida existência
        return repository.remover(id);
    }

    public List<Cliente> listarTodos() {
        return repository.listarTodos();
    }

    public Cliente buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
    }

    private void validarCampos(String nome, String cpf, String telefone, String email) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome é obrigatório.");
        if (cpf == null || cpf.trim().isEmpty())
            throw new IllegalArgumentException("CPF é obrigatório.");
        if (telefone == null || telefone.trim().isEmpty())
            throw new IllegalArgumentException("Telefone é obrigatório.");
        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("E-mail é obrigatório.");
    }
}
