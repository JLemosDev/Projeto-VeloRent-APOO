package ui;

import model.Disponibilidade;
import model.Veiculo;
import service.DisponibilidadeService;
import service.VeiculoService;
import util.Entrada;
import java.time.LocalDate;
import java.util.List;

public class MenuDisponibilidade {
    private final DisponibilidadeService service;
    private final VeiculoService veiculoService;

    public MenuDisponibilidade(DisponibilidadeService service, VeiculoService veiculoService) {
        this.service = service;
        this.veiculoService = veiculoService;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n========== MENU DISPONIBILIDADE ==========");
            System.out.println("1. Registrar disponibilidade");
            System.out.println("2. Editar disponibilidade");
            System.out.println("3. Excluir disponibilidade");
            System.out.println("4. Listar todas as disponibilidades");
            System.out.println("5. Listar por veículo");
            System.out.println("6. Verificar disponibilidade de veículo");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Entrada.lerInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> editar();
                case 3 -> excluir();
                case 4 -> listar();
                case 5 -> listarPorVeiculo();
                case 6 -> verificar();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n--- Registrar Disponibilidade ---");
        listarVeiculos();
        System.out.print("ID do veículo: ");
        int veiculoId = Entrada.lerInt();
        LocalDate inicio = Entrada.lerData("Data de início");
        LocalDate fim = Entrada.lerData("Data de fim");
        System.out.print("Disponível? (1=Sim / 0=Não): ");
        boolean disponivel = Entrada.lerInt() == 1;

        try {
            Disponibilidade d = service.cadastrar(veiculoId, inicio, fim, disponivel);
            System.out.println("Disponibilidade registrada: " + d);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void editar() {
        System.out.println("\n--- Editar Disponibilidade ---");
        listar();
        System.out.print("ID do registro a editar: ");
        int id = Entrada.lerInt();
        listarVeiculos();
        System.out.print("Novo ID do veículo: ");
        int veiculoId = Entrada.lerInt();
        LocalDate inicio = Entrada.lerData("Nova data de início");
        LocalDate fim = Entrada.lerData("Nova data de fim");
        System.out.print("Disponível? (1=Sim / 0=Não): ");
        boolean disponivel = Entrada.lerInt() == 1;

        try {
            Disponibilidade d = service.editar(id, veiculoId, inicio, fim, disponivel);
            System.out.println("Registro atualizado: " + d);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.println("\n--- Excluir Disponibilidade ---");
        listar();
        System.out.print("ID do registro a excluir: ");
        int id = Entrada.lerInt();
        try {
            service.excluir(id);
            System.out.println("Registro removido com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        List<Disponibilidade> lista = service.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum registro de disponibilidade encontrado.");
        } else {
            System.out.println("\n--- Registros de Disponibilidade ---");
            lista.forEach(System.out::println);
        }
    }

    private void listarPorVeiculo() {
        listarVeiculos();
        System.out.print("ID do veículo: ");
        int veiculoId = Entrada.lerInt();
        List<Disponibilidade> lista = service.listarPorVeiculo(veiculoId);
        if (lista.isEmpty()) {
            System.out.println("Nenhum registro encontrado para este veículo.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void verificar() {
        System.out.println("\n--- Verificar Disponibilidade ---");
        listarVeiculos();
        System.out.print("ID do veículo: ");
        int veiculoId = Entrada.lerInt();
        LocalDate inicio = Entrada.lerData("Data de início desejada");
        LocalDate fim = Entrada.lerData("Data de fim desejada");

        try {
            boolean disponivel = service.verificarDisponibilidade(veiculoId, inicio, fim);
            System.out.println(disponivel
                    ? "✔ Veículo DISPONÍVEL no período informado."
                    : "✘ Veículo INDISPONÍVEL no período informado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarVeiculos() {
        List<Veiculo> veiculos = veiculoService.listarTodos();
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            System.out.println("\n--- Veículos Cadastrados ---");
            veiculos.forEach(System.out::println);
        }
    }
}
