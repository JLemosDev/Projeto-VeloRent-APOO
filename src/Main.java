import repository.*;
import service.*;
import ui.*;

public class Main {
    public static void main(String[] args) {

        // ── Repositories ──────────────────────────────────────────
        ClienteRepository       clienteRepo  = new ClienteRepository();
        VeiculoRepository       veiculoRepo  = new VeiculoRepository();
        DisponibilidadeRepository dispRepo   = new DisponibilidadeRepository();
        OrcamentoRepository     orcRepo      = new OrcamentoRepository();
        PagamentoRepository     pagRepo      = new PagamentoRepository();

        // ── Services ──────────────────────────────────────────────
        ClienteService       clienteService  = new ClienteService(clienteRepo);
        VeiculoService       veiculoService  = new VeiculoService(veiculoRepo);
        DisponibilidadeService dispService   = new DisponibilidadeService(dispRepo, veiculoService);
        OrcamentoService     orcService      = new OrcamentoService(orcRepo, clienteService, veiculoService, dispService);
        PagamentoService     pagService      = new PagamentoService(pagRepo, orcService);

        // ── Menus ─────────────────────────────────────────────────
        MenuCliente        menuCliente  = new MenuCliente(clienteService);
        MenuVeiculo        menuVeiculo  = new MenuVeiculo(veiculoService);
        MenuDisponibilidade menuDisp    = new MenuDisponibilidade(dispService, veiculoService);
        MenuOrcamento      menuOrc      = new MenuOrcamento(orcService, clienteService, veiculoService);
        MenuPagamento      menuPag      = new MenuPagamento(pagService, orcService);

        // ── Loop principal ────────────────────────────────────────
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║   SISTEMA DE LOCADORA DE VEÍCULOS    ║");
            System.out.println("║   APOO-2026-01  |  UNIFACOL          ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println("  1. Clientes");
            System.out.println("  2. Veículos");
            System.out.println("  3. Disponibilidade");
            System.out.println("  4. Orçamentos");
            System.out.println("  5. Pagamentos");
            System.out.println("  0. Sair");
            System.out.print("Opção: ");
            opcao = util.Entrada.lerInt();

            switch (opcao) {
                case 1 -> menuCliente.exibir();
                case 2 -> menuVeiculo.exibir();
                case 3 -> menuDisp.exibir();
                case 4 -> menuOrc.exibir();
                case 5 -> menuPag.exibir();
                case 0 -> System.out.println("Encerrando sistema. Até logo!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
