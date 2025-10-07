package EcommerceFuncional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido {
    private final Cliente cliente;
    private final LocalDate dataCriacao;
    private final List<ItemPedido> itens = new ArrayList<>();
    private String status; // "Aberto", "Aguardando pagamento", "Pago", "Finalizado"

    public Pedido(Cliente cliente) {
        this.cliente = Objects.requireNonNull(cliente, "cliente obrigatório");
        this.dataCriacao = LocalDate.now();
        this.status = "Aberto";
    }

    public Cliente getCliente() { return cliente; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public String getStatus() { return status; }

    public void adicionarItem(ItemPedido item) {
        if (!"Aberto".equals(status)) {
            System.out.println("❌ Pedido não está aberto; não é possível adicionar itens.");
            return;
        }
        itens.add(item);
        System.out.println("✅ Item adicionado: " + item);
    }

    public void removerItem(int indice) {
        if (!"Aberto".equals(status)) {
            System.out.println("❌ Pedido não está aberto; não é possível remover itens.");
            return;
        }
        if (indice < 0 || indice >= itens.size()) {
            System.out.println("❌ Índice inválido.");
            return;
        }
        ItemPedido removido = itens.remove(indice);
        System.out.println("✅ Item removido: " + removido);
    }

    public void listarItens() {
        if (itens.isEmpty()) {
            System.out.println("[pedido sem itens]");
            return;
        }
        for (int i = 0; i < itens.size(); i++) {
            System.out.println((i) + " - " + itens.get(i));
        }
        System.out.printf("TOTAL: R$ %.2f%n", getTotal());
    }

    public double getTotal() {
        return itens.stream().mapToDouble(ItemPedido::getTotal).sum();
    }

    public void finalizar() {
        if (itens.isEmpty() || getTotal() <= 0) {
            System.out.println("❌ Pedido inválido para finalização. Adicione itens com preço válido.");
            return;
        }
        status = "Aguardando pagamento";
        System.out.println("🧾 Pedido finalizado. Status: " + status);
        System.out.println("📧 (simulado) Notificação enviada ao cliente: " + cliente.getNome());
    }

    public void pagar() {
        if (!"Aguardando pagamento".equals(status)) {
            System.out.println("❌ Pagamento não permitido. Status atual: " + status);
            return;
        }
        status = "Pago";
        System.out.println("💳 Pagamento confirmado. Status: " + status);
        System.out.println("📧 (simulado) Email de confirmação enviado ao cliente.");
    }

    public void entregar() {
        if (!"Pago".equals(status)) {
            System.out.println("❌ Entrega não permitida. Pedido precisa estar pago.");
            return;
        }
        status = "Finalizado";
        System.out.println("🚚 Pedido entregue. Status: " + status);
        System.out.println("📧 (simulado) Email de entrega enviado ao cliente.");
    }

    @Override
    public String toString() {
        return String.format("Pedido{cliente='%s', data=%s, status=%s, total=R$%.2f}",
                cliente.getNome(), dataCriacao, status, getTotal());
    }
}
