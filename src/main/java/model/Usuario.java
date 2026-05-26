package model;

import jakarta.persistence.*;

/**
 * Usuario — Entidade para autenticação e controle de perfil.
 *
 * Perfis definidos no diagrama de classes:
 *   ADMIN     → acesso total (inclui excluir clientes, editar/excluir pagamentos, etc.)
 *   ATENDENTE → acesso às operações do dia a dia (cadastrar, listar, orçamentos, etc.)
 *
 * A senha é armazenada como hash SHA-256 — nunca em texto puro.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 50)
    private String login;

    // Armazena o hash SHA-256 da senha (64 caracteres hex)
    @Column(nullable = false, length = 64)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Perfil perfil;

    public enum Perfil {
        ADMIN("Administrador"),
        ATENDENTE("Atendente");

        private final String descricao;
        Perfil(String descricao) { this.descricao = descricao; }
        public String getDescricao() { return descricao; }

        @Override
        public String toString() { return descricao; }
    }

    public Usuario() {}

    public Usuario(String login, String senha, Perfil perfil) {
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public int getId()       { return id; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public Perfil getPerfil(){ return perfil; }

    public void setLogin(String login)   { this.login = login; }
    public void setSenha(String senha)   { this.senha = senha; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    @Override
    public String toString() {
        return String.format("ID: %d | Login: %s | Perfil: %s", id, login, perfil);
    }
}
