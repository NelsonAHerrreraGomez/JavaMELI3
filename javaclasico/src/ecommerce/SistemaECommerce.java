package ecommerce;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaECommerce {

    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Clientes");
            System.out.println("2. Produtos");
            System.out.println("3. Pedidos");
            System.out.println("4. Exemplo de Caso de Uso");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> menuClientes();
                case 2 -> menuProdutos();
                case 3 -> menuPedidos();
                case 4 -> executarCasoDeUso();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // ===== CLIENTES =====
    private static void menuClientes() {
        int op;
        do {
            System.out.println("\n--- MENU CLIENTES ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Documento (CPF): ");
        String doc = sc.nextLine();
        System.out.print("Data de nascimento (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(sc.nextLine());

        Cliente c = new Cliente(nome, doc, data);
        if (!c.isMaiorDeIdade()) {
            System.out.println("❌ Cliente menor de idade. Cadastro não permitido.");
            return;
        }
        clientes.add(c);
        System.out.println("✅ Cliente cadastrado com sucesso!");
    }

    private static void listarClientes() {
        System.out.println("\n--- CLIENTES CADASTRADOS ---");
        if (clientes.isEmpty()) System.out.println("Nenhum cliente cadastrado.");
        else clientes.forEach(System.out::println);
    }

    // ===== PRODUTOS =====
    private static void menuProdutos() {
        int op;
        do {
            System.out.println("\n--- MENU PRODUTOS ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> cadastrarProduto();
                case 2 -> listarProdutos();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine();
        System.out.print("Preço: ");
        double preco = sc.nextDouble();
        System.out.print("Estoque: ");
        int estoque = sc.nextInt();
        produtos.add(new Produto(nome, preco, estoque));
        System.out.println("✅ Produto cadastrado!");
    }

    private static void listarProdutos() {
        System.out.println("\n--- PRODUTOS CADASTRADOS ---");
        if (produtos.isEmpty()) System.out.println("Nenhum produto cadastrado.");
        else produtos.forEach(System.out::println);
    }

    // ===== PEDIDOS =====
    private static void menuPedidos() {
        if (clientes.isEmpty() || produtos.isEmpty()) {
            System.out.println("⚠️ Cadastre pelo menos um cliente e um produto antes!");
            return;
        }

        Pedido pedidoAtual = null;
        int op;
        do {
            System.out.println("\n--- MENU PEDIDOS ---");
            System.out.println("1. Criar Pedido");
            System.out.println("2. Adicionar Item");
            System.out.println("3. Remover Item");
            System.out.println("4. Finalizar Pedido");
            System.out.println("5. Pagar Pedido");
            System.out.println("6. Entregar Pedido");
            System.out.println("7. Listar Pedidos");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> {
                    listarClientes();
                    System.out.print("Escolha o índice do cliente (1..n): ");
                    int i = sc.nextInt() - 1;
                    if (i < 0 || i >= clientes.size()) {
                        System.out.println("Índice inválido.");
                        break;
                    }
                    pedidoAtual = new Pedido(clientes.get(i));
                    pedidos.add(pedidoAtual);
                    System.out.println("📦 Pedido criado para " + clientes.get(i).getNome());
                }
                case 2 -> {
                    if (pedidoAtual == null) {
                        System.out.println("⚠️ Crie um pedido primeiro!");
                        break;
                    }
                    listarProdutos();
                    System.out.print("Escolha o produto: ");
                    int p = sc.nextInt() - 1;
                    System.out.print("Quantidade: ");
                    int qtd = sc.nextInt();
                    System.out.print("Preço de venda: ");
                    double precoVenda = sc.nextDouble();
                    pedidoAtual.adicionarItem(produtos.get(p), qtd, precoVenda);
                }
                case 3 -> {
                    if (pedidoAtual == null) break;
                    pedidoAtual.listarItens();
                    System.out.print("Índice do item: ");
                    int idx = sc.nextInt() - 1;
                    pedidoAtual.removerItem(idx);
                }
                case 4 -> { if (pedidoAtual != null) pedidoAtual.finalizar(); }
                case 5 -> { if (pedidoAtual != null) pedidoAtual.pagar(); }
                case 6 -> { if (pedidoAtual != null) pedidoAtual.entregar(); }
                case 7 -> pedidos.forEach(System.out::println);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    // ===== CASO DE USO AUTOMÁTICO =====
    private static void executarCasoDeUso() {
        System.out.println("\n===== DEMONSTRAÇÃO AUTOMÁTICA DO SISTEMA =====");

        Cliente cliente = new Cliente("Nelson Herrera Gomez", "12345678910", LocalDate.of(1975, 2, 26));
        clientes.add(cliente);
        System.out.println("👤 Cliente cadastrado: " + cliente);

        Produto notebook = new Produto("Notebook", 3500.0, 5);
        Produto mouse = new Produto("Mouse", 80.0, 20);
        Produto bike = new Produto( "Bike" ,  750.0,  12);

        produtos.add(notebook);
        produtos.add(mouse);
        System.out.println("🛒 Produtos cadastrados automaticamente.");

        Pedido pedido = new Pedido(cliente);
        pedidos.add(pedido);
        System.out.println("📦 Pedido criado para o cliente " + cliente.getNome());

        pedido.adicionarItem(notebook, 1, 3400.0);
        pedido.adicionarItem(mouse, 2, 75.0);
        pedido.adicionarItem(bike,1,750.0);
        pedido.listarItens();

        pedido.finalizar();
        pedido.pagar();
        pedido.entregar();

        System.out.println("\n===== DEMONSTRAÇÃO FINALIZADA =====");
        System.out.println(pedido);
    }
}
