package ui;

import model.Categoria;
import model.Usuario;
import model.Veiculo;
import service.VeiculoService;
import util.Entrada;
import java.util.List;

public class MenuVeiculo {
    private final VeiculoService service;
    private final Usuario usuarioLogado;

    public MenuVeiculo(VeiculoService service, Usuario usuarioLogado) {
        this.service = service;
        this.usuarioLogado = usuarioLogado;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n========== MENU VEÍCULOS ==========");
            System.out.println("1. Cadastrar veículo");
            System.out.println("2. Editar veículo");
            if (usuarioLogado.getPerfil() == Usuario.Perfil.ADMIN)
                System.out.println("3. Excluir veículo");
            System.out.println("4. Listar veículos");
            System.out.println("5. Listar por categoria");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Entrada.lerInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> editar();
                case 3 -> {
                    if (usuarioLogado.getPerfil() == Usuario.Perfil.ADMIN) excluir();
                    else System.out.println("Acesso restrito. Apenas Administradores podem excluir veículos.");
                }
                case 4 -> listar();
                case 5 -> listarPorCategoria();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n--- Cadastrar Veículo ---");
        System.out.print("Marca: ");
        String marca = Entrada.lerString();
        System.out.print("Modelo: ");
        String modelo = Entrada.lerString();
        System.out.print("Placa: ");
        String placa = Entrada.lerString();
        System.out.print("Ano: ");
        int ano = Entrada.lerInt();
        Categoria categoria = selecionarCategoria();
        System.out.print("Valor da diária (R$): ");
        double valorDiaria = Entrada.lerDouble();

        try {
            service.cadastrar(marca, modelo, placa, ano, categoria, valorDiaria);
            System.out.println("Veículo cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void editar() {
        System.out.println("\n--- Editar Veículo ---");
        listar();
        System.out.print("Informe o ID do veículo a editar: ");
        int id = Entrada.lerInt();
        System.out.print("Nova marca: ");
        String marca = Entrada.lerString();
        System.out.print("Novo modelo: ");
        String modelo = Entrada.lerString();
        System.out.print("Nova placa: ");
        String placa = Entrada.lerString();
        System.out.print("Novo ano: ");
        int ano = Entrada.lerInt();
        Categoria categoria = selecionarCategoria();
        System.out.print("Novo valor da diária (R$): ");
        double valorDiaria = Entrada.lerDouble();

        try {
            service.editar(id, marca, modelo, placa, ano, categoria, valorDiaria);
            System.out.println("Veículo atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.println("\n--- Excluir Veículo ---");
        listar();
        System.out.print("Informe o ID do veículo a excluir: ");
        int id = Entrada.lerInt();

        System.out.print("Tem certeza que deseja excluir este veículo? (1=Sim / 0=Não): ");
        if (Entrada.lerInt() != 1) { System.out.println("Operação cancelada."); return; }

        try {
            service.excluir(id);
            System.out.println("Veículo removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        List<Veiculo> lista = service.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            System.out.println("\n--- Lista de Veículos ---");
            lista.forEach(System.out::println);
        }
    }

    private void listarPorCategoria() {
        Categoria categoria = selecionarCategoria();
        List<Veiculo> lista = service.listarPorCategoria(categoria);
        if (lista.isEmpty()) {
            System.out.println("Nenhum veículo encontrado para a categoria: " + categoria);
        } else {
            System.out.println("\n--- Veículos - Categoria: " + categoria + " ---");
            lista.forEach(System.out::println);
        }
    }

    private Categoria selecionarCategoria() {
        Categoria[] categorias = Categoria.values();
        System.out.println("Categorias disponíveis:");
        for (int i = 0; i < categorias.length; i++) {
            System.out.printf("%d. %s%n", i + 1, categorias[i]);
        }
        System.out.print("Escolha a categoria: ");
        int idx = Entrada.lerInt();
        if (idx < 1 || idx > categorias.length) {
            System.out.println("Opção inválida. Selecionando Econômico.");
            return Categoria.ECONOMICO;
        }
        return categorias[idx - 1];
    }
}
