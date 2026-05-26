package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Orcamento {
    private int id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double valorEstimado;

    public Orcamento(int id, Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) {
        this.id = id;
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
