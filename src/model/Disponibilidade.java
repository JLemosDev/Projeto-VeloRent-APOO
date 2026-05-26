package model;

import java.time.LocalDate;

public class Disponibilidade {
    private int id;
    private Veiculo veiculo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean disponivel;

    public Disponibilidade(int id, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim, boolean disponivel) {
        this.id = id;
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
