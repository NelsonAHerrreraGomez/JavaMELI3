package EcommerceFuncional;

import java.util.Objects;

public class ItemPedido {
    private final Produto produto;
    private final int quantidade;
    private final double precoUnitario;

    public ItemPedido(Produto produto, int quantidade, double precoUnitario) {
        this.produto = Objects.requireNonNull(produto, "produto é obrigatório");
        if (quantidade <= 0) throw new IllegalArgumentException("quantidade deve ser > 0");
        if (precoUnitario <= 0) throw new IllegalArgumentException("precoUnitario deve ser > 0");
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoUnitario() { return precoUnitario; }

    public double getTotal() { return quantidade * precoUnitario; }

    @Override
    public String toString() {
        return String.format("%s x%d @ R$%.2f = R$%.2f",
                produto.getNome(), quantidade, precoUnitario, getTotal());
    }
}
