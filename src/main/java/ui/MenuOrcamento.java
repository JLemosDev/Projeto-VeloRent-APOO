package ui;

import model.Cliente;
import model.Orcamento;
import model.Usuario;
import model.Veiculo;
import service.ClienteService;
import service.OrcamentoService;
import service.VeiculoService;
import util.Entrada;
import java.time.LocalDate;
import java.util.List;

public class MenuOrcamento {
    private final OrcamentoService service;
    private final ClienteService clienteService;
    private final VeiculoService veiculoService;
    private final Usuario usuarioLogado;

    public MenuOrcamento(OrcamentoService service, ClienteService clienteService,
                         VeiculoService veiculoService, Usuario usuarioLogado) {
        this.service = service;
        this.clienteService = clienteService;
        this.veiculoService = veiculoService;
        this.usuarioLogado = usuarioLogado;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n========== MENU ORÇAMENTOS ==========");
            System.out.println("1. Criar orçamento");
            System.out.println("2. Editar orçamento");
            System.out.println("3. Excluir orçamento");
            System.out.println("4. Listar orçamentos");
            System.out.println("5. Listar orçamentos por cliente");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Entrada.lerInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> editar();
                case 3 -> excluir();
                case 4 -> listar();
                case 5 -> listarPorCliente();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n--- Criar Orçamento ---");
        listarClientes();
        System.out.print("ID do cliente: ");
        int clienteId = Entrada.lerInt();
        listarVeiculos();
        System.out.print("ID do veículo: ");
        int veiculoId = Entrada.lerInt();
        LocalDate inicio = Entrada.lerData("Data de início");
        LocalDate fim = Entrada.lerData("Data de fim");

        try {
            Orcamento o = service.cadastrar(clienteId, veiculoId, inicio, fim);
            System.out.println("Orçamento criado com sucesso!");
            System.out.println(o);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void editar() {
        System.out.println("\n--- Editar Orçamento ---");
        listar();
        System.out.print("ID do orçamento a editar: ");
        int id = Entrada.lerInt();
        listarClientes();
        System.out.print("Novo ID do cliente: ");
        int clienteId = Entrada.lerInt();
        listarVeiculos();
        System.out.print("Novo ID do veículo: ");
        int veiculoId = Entrada.lerInt();
        LocalDate inicio = Entrada.lerData("Nova data de início");
        LocalDate fim = Entrada.lerData("Nova data de fim");

        try {
            Orcamento o = service.editar(id, clienteId, veiculoId, inicio, fim);
            System.out.println("Orçamento atualizado com sucesso!");
            System.out.println(o);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.println("\n--- Excluir Orçamento ---");
        listar();
        System.out.print("ID do orçamento a excluir: ");
        int id = Entrada.lerInt();

        System.out.print("Tem certeza que deseja excluir este orçamento? (1=Sim / 0=Não): ");
        if (Entrada.lerInt() != 1) { System.out.println("Operação cancelada."); return; }

        try {
            service.excluir(id);
            System.out.println("Orçamento removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        List<Orcamento> lista = service.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum orçamento registrado.");
        } else {
            System.out.println("\n--- Lista de Orçamentos ---");
            lista.forEach(System.out::println);
        }
    }

    private void listarPorCliente() {
        listarClientes();
        System.out.print("ID do cliente: ");
        int clienteId = Entrada.lerInt();
        List<Orcamento> lista = service.listarPorCliente(clienteId);
        if (lista.isEmpty()) {
            System.out.println("Nenhum orçamento encontrado para este cliente.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteService.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            System.out.println("\n--- Clientes ---");
            clientes.forEach(c -> System.out.printf("ID: %d | %s | CPF: %s%n",
                    c.getId(), c.getNome(), c.getCpf()));
        }
    }

    private void listarVeiculos() {
        List<Veiculo> veiculos = veiculoService.listarTodos();
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            System.out.println("\n--- Veículos ---");
            veiculos.forEach(System.out::println);
        }
    }
}
