package ecommerce;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Cliente cliente;
    private LocalDate dataCriacao;
    private String status;
    private List<ItemPedido> itens;
    private double valorTotal;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.dataCriacao = LocalDate.now();
        this.status = "Aberto";
        this.itens = new ArrayList<>();
        this.valorTotal = 0.0;
    }

    public void adicionarItem(Produto produto, int quantidade, double precoUnitario) {
        if (!status.equals("Aberto")) {
            System.out.println("❌ Não é possível adicionar itens: pedido não está aberto.");
            return;
        }
        ItemPedido item = new ItemPedido(produto, quantidade, precoUnitario);
        itens.add(item);
        valorTotal += item.getSubtotal();
        System.out.println("✅ Item adicionado: " + produto.getNome());
    }

    public void removerItem(int indice) {
        if (!status.equals("Aberto")) {
            System.out.println("❌ Não é possível remover itens: pedido não está aberto.");
            return;
        }
        if (indice < 0 || indice >= itens.size()) {
            System.out.println("❌ Índice inválido!");
            return;
        }
        ItemPedido removido = itens.remove(indice);
        valorTotal -= removido.getSubtotal();
        System.out.println("✅ Item removido: " + removido);
    }

    public void listarItens() {
        System.out.println("\n--- Itens do Pedido ---");
        if (itens.isEmpty()) {
            System.out.println("Nenhum item adicionado.");
        } else {
            for (int i = 0; i < itens.size(); i++) {
                System.out.println((i + 1) + ". " + itens.get(i));
            }
            System.out.println("Valor total: R$" + valorTotal);
        }
    }

    public void finalizar() {
        if (itens.isEmpty() || valorTotal <= 0) {
            System.out.println("❌ Pedido inválido. Não pode ser finalizado.");
            return;
        }
        status = "Aguardando Pagamento";
        System.out.println("💳 Pedido finalizado. Status: " + status);
    }

    public void pagar() {
        if (!status.equals("Aguardando Pagamento")) {
            System.out.println("❌ Pedido não pode ser pago. Status atual: " + status);
            return;
        }
        status = "Pago";
        System.out.println("✅ Pagamento realizado com sucesso!");
    }

    public void entregar() {
        if (!status.equals("Pago")) {
            System.out.println("❌ Pedido ainda não foi pago.");
            return;
        }
        status = "Finalizado";
        System.out.println("📦 Pedido entregue ao cliente!");
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "cliente=" + cliente.getNome() +
                ", data=" + dataCriacao +
                ", status='" + status + '\'' +
                ", total=R$" + valorTotal +
                '}';
    }
}
