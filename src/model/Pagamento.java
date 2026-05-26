package model;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private Orcamento orcamento;
    private double valorPago;
    private LocalDate dataPagamento;
    private FormaPagamento formaPagamento;

    public Pagamento(int id, Orcamento orcamento, double valorPago, LocalDate dataPagamento, FormaPagamento formaPagamento) {
        this.id = id;
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
