package ui;

import model.FormaPagamento;
import model.Orcamento;
import model.Pagamento;
import service.OrcamentoService;
import service.PagamentoService;
import util.Entrada;
import java.time.LocalDate;
import java.util.List;

public class MenuPagamento {
    private final PagamentoService service;
    private final OrcamentoService orcamentoService;

    public MenuPagamento(PagamentoService service, OrcamentoService orcamentoService) {
        this.service = service;
        this.orcamentoService = orcamentoService;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n========== MENU PAGAMENTOS ==========");
            System.out.println("1. Registrar pagamento");
            System.out.println("2. Editar pagamento");
            System.out.println("3. Excluir pagamento");
            System.out.println("4. Listar pagamentos");
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
        System.out.println("\n--- Registrar Pagamento ---");
        listarOrcamentos();
        System.out.print("ID do orçamento: ");
        int orcamentoId = Entrada.lerInt();

        try {
            Orcamento orc = orcamentoService.buscarPorId(orcamentoId);
            System.out.printf("Valor do orçamento: R$ %.2f%n", orc.getValorEstimado());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        System.out.print("Valor pago (R$): ");
        double valor = Entrada.lerDouble();
        LocalDate data = Entrada.lerData("Data do pagamento");
        FormaPagamento forma = selecionarFormaPagamento();

        try {
            Pagamento p = service.cadastrar(orcamentoId, valor, data, forma);
            System.out.println("Pagamento registrado com sucesso! " + p);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void editar() {
        System.out.println("\n--- Editar Pagamento ---");
        listar();
        System.out.print("ID do pagamento a editar: ");
        int id = Entrada.lerInt();
        listarOrcamentos();
        System.out.print("Novo ID do orçamento: ");
        int orcamentoId = Entrada.lerInt();
        System.out.print("Novo valor pago (R$): ");
        double valor = Entrada.lerDouble();
        LocalDate data = Entrada.lerData("Nova data do pagamento");
        FormaPagamento forma = selecionarFormaPagamento();

        try {
            Pagamento p = service.editar(id, orcamentoId, valor, data, forma);
            System.out.println("Pagamento atualizado: " + p);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.println("\n--- Excluir Pagamento ---");
        listar();
        System.out.print("ID do pagamento a excluir: ");
        int id = Entrada.lerInt();
        try {
            service.excluir(id);
            System.out.println("Pagamento removido com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        List<Pagamento> lista = service.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
        } else {
            System.out.println("\n--- Lista de Pagamentos ---");
            lista.forEach(System.out::println);
        }
    }

    private void listarOrcamentos() {
        List<Orcamento> lista = orcamentoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum orçamento cadastrado.");
        } else {
            System.out.println("\n--- Orçamentos disponíveis ---");
            lista.forEach(System.out::println);
        }
    }

    private FormaPagamento selecionarFormaPagamento() {
        FormaPagamento[] formas = FormaPagamento.values();
        System.out.println("Formas de pagamento:");
        for (int i = 0; i < formas.length; i++) {
            System.out.printf("%d. %s%n", i + 1, formas[i]);
        }
        System.out.print("Escolha a forma de pagamento: ");
        int idx = Entrada.lerInt();
        if (idx < 1 || idx > formas.length) {
            System.out.println("Opção inválida. Selecionando Dinheiro.");
            return FormaPagamento.DINHEIRO;
        }
        return formas[idx - 1];
    }
}
