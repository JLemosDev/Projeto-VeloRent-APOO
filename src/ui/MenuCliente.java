package ui;

import model.Cliente;
import service.ClienteService;
import util.Entrada;
import java.util.List;

public class MenuCliente {
    private final ClienteService service;

    public MenuCliente(ClienteService service) {
        this.service = service;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n========== MENU CLIENTES ==========");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Editar cliente");
            System.out.println("3. Excluir cliente");
            System.out.println("4. Listar clientes");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Entrada.lerInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> editar();
                case 3 -> excluir();
                case 4 -> listar();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n--- Cadastrar Cliente ---");
        System.out.print("Nome: ");
        String nome = Entrada.lerString();
        System.out.print("CPF: ");
        String cpf = Entrada.lerString();
        System.out.print("Telefone: ");
        String telefone = Entrada.lerString();
        System.out.print("Endereço: ");
        String endereco = Entrada.lerString();
        System.out.print("E-mail: ");
        String email = Entrada.lerString();

        try {
            Cliente c = service.cadastrar(nome, cpf, telefone, endereco, email);
            System.out.println("Cliente cadastrado com sucesso! " + c);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void editar() {
        System.out.println("\n--- Editar Cliente ---");
        listar();
        System.out.print("Informe o ID do cliente a editar: ");
        int id = Entrada.lerInt();
        System.out.print("Novo nome: ");
        String nome = Entrada.lerString();
        System.out.print("Novo CPF: ");
        String cpf = Entrada.lerString();
        System.out.print("Novo telefone: ");
        String telefone = Entrada.lerString();
        System.out.print("Novo endereço: ");
        String endereco = Entrada.lerString();
        System.out.print("Novo e-mail: ");
        String email = Entrada.lerString();

        try {
            Cliente c = service.editar(id, nome, cpf, telefone, endereco, email);
            System.out.println("Cliente atualizado: " + c);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.println("\n--- Excluir Cliente ---");
        listar();
        System.out.print("Informe o ID do cliente a excluir: ");
        int id = Entrada.lerInt();
        try {
            service.excluir(id);
            System.out.println("Cliente removido com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        List<Cliente> lista = service.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            System.out.println("\n--- Lista de Clientes ---");
            lista.forEach(System.out::println);
        }
    }
}
