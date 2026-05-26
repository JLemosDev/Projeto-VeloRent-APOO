package model;

/**
 * Categoria — enum armazenado como String no banco.
 *
 * O Hibernate pode salvar enums de dois jeitos:
 *   ORDINAL → salva o número (0, 1, 2...) — frágil: mudar a ordem quebra tudo
 *   STRING  → salva o nome ("ECONOMICO") — recomendado, legível e seguro
 *
 * A anotação @Enumerated(EnumType.STRING) é aplicada nos campos que usam este enum.
 */
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
