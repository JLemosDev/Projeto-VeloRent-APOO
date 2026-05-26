package model;

import jakarta.persistence.*;

/**
 * Cliente — Entidade mapeada para a tabela "clientes"
 *
 * Anotações principais:
 *   @Entity   → esta classe vira uma tabela no banco
 *   @Table    → define o nome da tabela (opcional; sem ela usa o nome da classe)
 *   @Id       → campo que é a chave primária
 *   @GeneratedValue → o banco gera o ID automaticamente (auto-increment)
 *   @Column   → personaliza a coluna: nome, tamanho, obrigatoriedade, unicidade
 */
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // nullable=false → NOT NULL no banco (campo obrigatório)
    @Column(nullable = false, length = 100)
    private String nome;

    // unique=true → o banco rejeita CPFs duplicados
    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    @Column(length = 20)
    private String telefone;

    @Column(length = 200)
    private String endereco;

    @Column(length = 100)
    private String email;

    // ⚠️ OBRIGATÓRIO: o Hibernate usa reflexão para instanciar
    //    objetos ao ler do banco. Precisa de construtor sem argumentos.
    public Cliente() {}

    // Construtor sem id (o banco gera)
    public Cliente(String nome, String cpf, String telefone, String endereco, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }
    public String getEmail() { return email; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | CPF: %s | Tel: %s | Email: %s | Endereço: %s",
                id, nome, cpf, telefone, email, endereco);
    }
}
