package model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Disponibilidade — Entidade com relacionamento @ManyToOne.
 *
 * Relacionamento Many-To-One:
 *   Muitas disponibilidades podem pertencer a UM veículo.
 *   No banco isso vira uma coluna "veiculo_id" (chave estrangeira) nesta tabela.
 *
 * @JoinColumn(name = "veiculo_id") → define o nome da coluna FK no banco.
 *
 * LocalDate é suportado nativamente pelo Hibernate 6 — sem conversores extras.
 */
@Entity
@Table(name = "disponibilidades")
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @ManyToOne: N disponibilidades → 1 veiculo
    // fetch = LAZY: o Veiculo só é carregado do banco quando você chamar getVeiculo()
    //               (evita carregar objetos desnecessariamente)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private boolean disponivel;

    public Disponibilidade() {}

    public Disponibilidade(Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim, boolean disponivel) {
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.disponivel = disponivel;
    }

    public int getId() { return id; }
    public Veiculo getVeiculo() { return veiculo; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public boolean isDisponivel() { return disponivel; }

    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    @Override
    public String toString() {
        return String.format("ID: %d | Veículo: %s %s (Placa: %s) | Período: %s a %s | Status: %s",
                id, veiculo.getMarca(), veiculo.getModelo(), veiculo.getPlaca(),
                dataInicio, dataFim, disponivel ? "Disponível" : "Indisponível");
    }
}
