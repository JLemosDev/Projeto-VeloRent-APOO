import model.Usuario;
import repository.*;
import service.*;
import ui.*;
import util.HibernateUtil;

public class Main {
    public static void main(String[] args) {

        // ── Repositories ──────────────────────────────────────────────────────
        UsuarioRepository         usuarioRepo  = new UsuarioRepository();
        ClienteRepository         clienteRepo  = new ClienteRepository();
        VeiculoRepository         veiculoRepo  = new VeiculoRepository();
        DisponibilidadeRepository dispRepo     = new DisponibilidadeRepository();
        OrcamentoRepository       orcRepo      = new OrcamentoRepository();
        PagamentoRepository       pagRepo      = new PagamentoRepository();

        // ── Services ──────────────────────────────────────────────────────────
        UsuarioService       usuarioService  = new UsuarioService(usuarioRepo);
        ClienteService       clienteService  = new ClienteService(clienteRepo);
        VeiculoService       veiculoService  = new VeiculoService(veiculoRepo);
        DisponibilidadeService dispService   = new DisponibilidadeService(dispRepo, veiculoService);
        OrcamentoService     orcService      = new OrcamentoService(orcRepo, clienteService, veiculoService, dispService);
        PagamentoService     pagService      = new PagamentoService(pagRepo, orcService);

        // RF-001: garante que exista ao menos um admin ao iniciar pela primeira vez
        usuarioService.garantirAdminPadrao();

        // ── Login (US-001) ─────────────────────────────────────────────────────
        MenuLogin menuLogin = new MenuLogin(usuarioService);
        Usuario usuarioLogado = menuLogin.exibir();

        // ── Menus (recebem o usuário logado para controle de perfil) ───────────
        MenuCliente         menuCliente  = new MenuCliente(clienteService, usuarioLogado);
        MenuVeiculo         menuVeiculo  = new MenuVeiculo(veiculoService, usuarioLogado);
        MenuDisponibilidade menuDisp     = new MenuDisponibilidade(dispService, veiculoService, usuarioLogado);
        MenuOrcamento       menuOrc      = new MenuOrcamento(orcService, clienteService, veiculoService, usuarioLogado);
        MenuPagamento       menuPag      = new MenuPagamento(pagService, orcService, usuarioLogado);
        MenuUsuario         menuUsuario  = new MenuUsuario(usuarioService, usuarioLogado);

        // ── Loop principal ─────────────────────────────────────────────────────
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║   SISTEMA DE LOCADORA DE VEÍCULOS    ║");
            System.out.printf( "║   Usuário: %-10s [%-11s]  ║%n",
                    usuarioLogado.getLogin(), usuarioLogado.getPerfil());
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println("  1. Clientes");
            System.out.println("  2. Veículos");
            System.out.println("  3. Disponibilidade");
            System.out.println("  4. Orçamentos");
            System.out.println("  5. Pagamentos");
            if (usuarioLogado.getPerfil() == Usuario.Perfil.ADMIN)
                System.out.println("  6. Usuários");
            System.out.println("  0. Sair");
            System.out.print("Opção: ");
            opcao = util.Entrada.lerInt();

            switch (opcao) {
                case 1 -> menuCliente.exibir();
                case 2 -> menuVeiculo.exibir();
                case 3 -> menuDisp.exibir();
                case 4 -> menuOrc.exibir();
                case 5 -> menuPag.exibir();
                case 6 -> {
                    if (usuarioLogado.getPerfil() == Usuario.Perfil.ADMIN) menuUsuario.exibir();
                    else System.out.println("Opção inválida.");
                }
                case 0 -> System.out.println("Encerrando sistema. Até logo!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        HibernateUtil.shutdown();
    }
}
