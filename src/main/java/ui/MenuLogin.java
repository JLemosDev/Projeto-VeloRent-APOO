package ui;

import model.Usuario;
import service.UsuarioService;
import util.Entrada;

/**
 * MenuLogin — implementa US-001 (RF-001): autenticação com usuário e senha.
 *
 * CN01 – Credenciais inválidas: exibe "Usuário ou senha inválidos. Tente novamente."
 * CN02 – Credenciais válidas:  autentica e retorna o Usuario logado.
 *
 * Máximo de 3 tentativas antes de encerrar o programa (proteção básica contra força bruta).
 */
public class MenuLogin {

    private static final int MAX_TENTATIVAS = 3;
    private final UsuarioService service;

    public MenuLogin(UsuarioService service) {
        this.service = service;
    }

    /**
     * Exibe a tela de login e retorna o Usuario autenticado.
     * Se o usuário errar MAX_TENTATIVAS vezes, o programa é encerrado.
     */
    public Usuario exibir() {
        int tentativas = 0;

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE LOCADORA DE VEÍCULOS    ║");
        System.out.println("║   APOO-2026-01  |  UNIFACOL          ║");
        System.out.println("╚══════════════════════════════════════╝");

        while (tentativas < MAX_TENTATIVAS) {
            System.out.println("\n---------- Login ----------");
            System.out.print("Usuário: ");
            String login = Entrada.lerString();
            System.out.print("Senha:   ");
            String senha = Entrada.lerString();

            try {
                Usuario usuario = service.autenticar(login, senha);
                System.out.printf("%nBem-vindo(a), %s! [%s]%n", usuario.getLogin(), usuario.getPerfil());
                return usuario;
            } catch (IllegalArgumentException e) {
                tentativas++;
                System.out.println(e.getMessage()); // "Usuário ou senha inválidos. Tente novamente."
                if (tentativas < MAX_TENTATIVAS) {
                    System.out.printf("Tentativa %d de %d.%n", tentativas, MAX_TENTATIVAS);
                }
            }
        }

        System.out.println("Número máximo de tentativas atingido. Encerrando sistema.");
        System.exit(1);
        return null; // nunca alcançado — satisfaz o compilador
    }
}
