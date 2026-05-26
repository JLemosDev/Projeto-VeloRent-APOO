package model;

import jakarta.persistence.*;

/**
 * Veiculo — Entidade com campo enum mapeado.
 *
 * Novidade aqui: o campo "categoria" é um enum.
 * Usamos @Enumerated(EnumType.STRING) para salvar o nome
 * ("ECONOMICO", "SUV"…) em vez do índice numérico.
 */
@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 80)
    private String modelo;

    @Column(unique = true, nullable = false, length = 10)
    private String placa;

    @Column(nullable = false)
    private int ano;

    // @Enumerated: diz ao Hibernate como serializar o enum
    // EnumType.STRING → salva "ECONOMICO", "SUV" etc. (legível no banco)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Categoria categoria;

    @Column(nullable = false)
    private double valorDiaria;

    public Veiculo() {}

    public Veiculo(String marca, String modelo, String placa, int ano, Categoria categoria, double valorDiaria) {
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
        this.categoria = categoria;
        this.valorDiaria = valorDiaria;
    }

    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public int getAno() { return ano; }
    public Categoria getCategoria() { return categoria; }
    public double getValorDiaria() { return valorDiaria; }

    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setPlaca(String placa) { this.placa = placa; }
    public void setAno(int ano) { this.ano = ano; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setValorDiaria(double valorDiaria) { this.valorDiaria = valorDiaria; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s %s (%d) | Placa: %s | Categoria: %s | Diária: R$ %.2f",
                id, marca, modelo, ano, placa, categoria, valorDiaria);
    }
}
