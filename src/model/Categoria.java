package model;

public enum Categoria {
    ECONOMICO("Econômico"),
    INTERMEDIARIO("Intermediário"),
    EXECUTIVO("Executivo"),
    SUV("SUV"),
    PICKUP("Pickup"),
    MINIVAN("Minivan");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() { return descricao; }

    @Override
    public String toString() { return descricao; }
}
