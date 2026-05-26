package service;

import model.Usuario;
import repository.UsuarioRepository;
import util.SenhaUtil;

import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Tenta autenticar com login/senha.
     * Retorna o Usuario autenticado ou lança exceção com mensagem amigável.
     */
    public Usuario autenticar(String login, String senha) {
        if (login == null || login.trim().isEmpty() || senha == null || senha.isEmpty())
            throw new IllegalArgumentException("Usuário ou senha inválidos. Tente novamente.");

        Optional<Usuario> opt = repository.buscarPorLogin(login.trim());
        if (opt.isEmpty() || !SenhaUtil.verificar(senha, opt.get().getSenha()))
            throw new IllegalArgumentException("Usuário ou senha inválidos. Tente novamente.");

        return opt.get();
    }

    public Usuario cadastrar(String login, String senha, Usuario.Perfil perfil) {
        if (login == null || login.trim().isEmpty())
            throw new IllegalArgumentException("Login é obrigatório.");
        if (senha == null || senha.length() < 4)
            throw new IllegalArgumentException("A senha deve ter pelo menos 4 caracteres.");
        if (repository.buscarPorLogin(login.trim()).isPresent())
            throw new IllegalArgumentException("Já existe um usuário com este login.");

        return repository.criar(login.trim(), SenhaUtil.hash(senha), perfil);
    }

    public Usuario alterarSenha(int id, String novaSenha) {
        if (novaSenha == null || novaSenha.length() < 4)
            throw new IllegalArgumentException("A senha deve ter pelo menos 4 caracteres.");

        Usuario usuario = buscarPorId(id);
        usuario.setSenha(SenhaUtil.hash(novaSenha));
        return repository.salvar(usuario);
    }

    public boolean excluir(int id) {
        buscarPorId(id);
        return repository.remover(id);
    }

    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }

    public Usuario buscarPorId(int id) {
        return repository.listarTodos().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
    }

    /**
     * Garante que exista pelo menos um ADMIN no sistema.
     * Chamado na inicialização: cria o admin padrão se o banco estiver vazio.
     */
    public void garantirAdminPadrao() {
        if (repository.estaVazio()) {
            repository.criar("admin", SenhaUtil.hash("admin123"), Usuario.Perfil.ADMIN);
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║  Usuário padrão criado:                      ║");
            System.out.println("║  Login: admin  |  Senha: admin123            ║");
            System.out.println("║  Altere a senha após o primeiro acesso!      ║");
            System.out.println("╚══════════════════════════════════════════════╝");
        }
    }
}
