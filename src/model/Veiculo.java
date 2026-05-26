package model;

public class Veiculo {
    private int id;
    private String marca;
    private String modelo;
    private String placa;
    private int ano;
    private Categoria categoria;
    private double valorDiaria;

    public Veiculo(int id, String marca, String modelo, String placa, int ano, Categoria categoria, double valorDiaria) {
        this.id = id;
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
