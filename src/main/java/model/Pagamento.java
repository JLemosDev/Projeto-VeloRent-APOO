package model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Pagamento — Entidade com relacionamento para Orcamento e enum FormaPagamento.
 *
 * Cadeia de relacionamentos no banco:
 *   pagamentos.orcamento_id → orcamentos.id
 *   orcamentos.cliente_id   → clientes.id
 *   orcamentos.veiculo_id   → veiculos.id
 */
@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orcamento_id", nullable = false)
    private Orcamento orcamento;

    @Column(nullable = false)
    private double valorPago;

    @Column(nullable = false)
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FormaPagamento formaPagamento;

    public Pagamento() {}

    public Pagamento(Orcamento orcamento, double valorPago, LocalDate dataPagamento, FormaPagamento formaPagamento) {
        this.orcamento = orcamento;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.formaPagamento = formaPagamento;
    }

    public int getId() { return id; }
    public Orcamento getOrcamento() { return orcamento; }
    public double getValorPago() { return valorPago; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }

    public void setOrcamento(Orcamento orcamento) { this.orcamento = orcamento; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }

    @Override
    public String toString() {
        return String.format("ID: %d | Cliente: %s | Veículo: %s %s | Valor: R$ %.2f | Data: %s | Forma: %s",
                id,
                orcamento.getCliente().getNome(),
                orcamento.getVeiculo().getMarca(),
                orcamento.getVeiculo().getModelo(),
                valorPago, dataPagamento, formaPagamento);
    }
}
