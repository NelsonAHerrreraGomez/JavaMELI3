package EcommerceFuncional;

import java.util.Objects;

public class Produto {
    private final String nome;
    private double preco;
    private int estoque;

    public Produto(String nome, double preco, int estoque) {
        this.nome = Objects.requireNonNull(nome, "nome é obrigatório");
        this.preco = preco;
        this.estoque = estoque;
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }

    public void setPreco(double preco) { this.preco = preco; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    @Override
    public String toString() {
        return String.format("Produto{name='%s', preco=R$%.2f, estoque=%d}", nome, preco, estoque);
    }
}
