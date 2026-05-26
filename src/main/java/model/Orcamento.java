package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Orcamento — Entidade com dois relacionamentos @ManyToOne.
 *
 * Um orçamento pertence a UM cliente e a UM veículo.
 * No banco: colunas "cliente_id" e "veiculo_id" como chaves estrangeiras.
 *
 * valorEstimado é calculado automaticamente sempre que as datas mudam.
 * Salvamos o valor no banco para evitar recalcular a cada leitura.
 */
@Entity
@Table(name = "orcamentos")
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private double valorEstimado;

    public Orcamento() {}

    public Orcamento(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorEstimado = calcularValor();
    }

    private double calcularValor() {
        long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
        if (dias <= 0) dias = 1;
        return dias * veiculo.getValorDiaria();
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Veiculo getVeiculo() { return veiculo; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public double getValorEstimado() { return valorEstimado; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        this.valorEstimado = calcularValor();
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        this.valorEstimado = calcularValor();
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
        this.valorEstimado = calcularValor();
    }

    public long getDias() {
        long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
        return dias <= 0 ? 1 : dias;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Cliente: %s | Veículo: %s %s | Período: %s a %s (%d dias) | Valor Estimado: R$ %.2f",
                id, cliente.getNome(), veiculo.getMarca(), veiculo.getModelo(),
                dataInicio, dataFim, getDias(), valorEstimado);
    }
}
