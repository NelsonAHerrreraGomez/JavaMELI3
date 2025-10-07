package ecommerce;


public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private double precoUnitario;

    public ItemPedido(Produto produto, int quantidade, double precoUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public double getSubtotal() {
        return quantidade * precoUnitario;
    }

    @Override
    public String toString() {
        return produto.getNome() + " | Qtd: " + quantidade +
                " | Preço: R$" + precoUnitario +
                " | Subtotal: R$" + getSubtotal();
    }
}
