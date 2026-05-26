package ui;

import model.Usuario;
import service.UsuarioService;
import util.Entrada;
import java.util.List;

/**
 * MenuUsuario — disponível apenas para Administradores.
 * Permite cadastrar novos usuários, alterar senhas e excluir contas.
 */
public class MenuUsuario {

    private final UsuarioService service;
    private final Usuario usuarioLogado;

    public MenuUsuario(UsuarioService service, Usuario usuarioLogado) {
        this.service = service;
        this.usuarioLogado = usuarioLogado;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n========== MENU USUÁRIOS ==========");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Alterar senha de usuário");
            System.out.println("3. Excluir usuário");
            System.out.println("4. Listar usuários");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Entrada.lerInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> alterarSenha();
                case 3 -> excluir();
                case 4 -> listar();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n--- Cadastrar Usuário ---");
        System.out.print("Login: ");
        String login = Entrada.lerString();
        System.out.print("Senha (mínimo 4 caracteres): ");
        String senha = Entrada.lerString();

        System.out.println("Perfil:");
        System.out.println("1. Administrador");
        System.out.println("2. Atendente");
        System.out.print("Escolha: ");
        int perfilOpcao = Entrada.lerInt();
        Usuario.Perfil perfil = perfilOpcao == 1 ? Usuario.Perfil.ADMIN : Usuario.Perfil.ATENDENTE;

        try {
            Usuario u = service.cadastrar(login, senha, perfil);
            System.out.println("Usuário cadastrado com sucesso! " + u);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void alterarSenha() {
        System.out.println("\n--- Alterar Senha ---");
        listar();
        System.out.print("ID do usuário: ");
        int id = Entrada.lerInt();
        System.out.print("Nova senha (mínimo 4 caracteres): ");
        String novaSenha = Entrada.lerString();
        try {
            service.alterarSenha(id, novaSenha);
            System.out.println("Senha alterada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.println("\n--- Excluir Usuário ---");
        listar();
        System.out.print("ID do usuário a excluir: ");
        int id = Entrada.lerInt();

        // Impede auto-exclusão
        if (id == usuarioLogado.getId()) {
            System.out.println("Você não pode excluir o próprio usuário enquanto está logado.");
            return;
        }

        System.out.print("Tem certeza que deseja excluir este usuário? (1=Sim / 0=Não): ");
        if (Entrada.lerInt() != 1) { System.out.println("Operação cancelada."); return; }

        try {
            service.excluir(id);
            System.out.println("Usuário removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        List<Usuario> lista = service.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            System.out.println("\n--- Usuários Cadastrados ---");
            lista.forEach(System.out::println);
        }
    }
}
