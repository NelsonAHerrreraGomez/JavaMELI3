package EcommerceFuncional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SistemaECommerce {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Produto> produtos = new ArrayList<>();
    private static final List<Pedido> pedidos = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Clientes");
            System.out.println("2. Produtos");
            System.out.println("3. Pedidos");
            System.out.println("4. Caso de Uso Funcional (Exemplo)");
            System.out.println("0. Sair");
            opcao = readInt("Escolha: ");

            switch (opcao) {
                case 1: menuClientes(); break;
                case 2: menuProdutos(); break;
                case 3: menuPedidos(); break;
                case 4: casoDeUsoFuncional(); break;
                case 0: System.out.println("Encerrando..."); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // ------------------ Helpers de leitura seguros ------------------
    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            String s = readLine(prompt);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            String s = readLine(prompt);
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número decimal (ex: 1234.56).");
            }
        }
    }

    // ------------------ MENU CLIENTES ------------------
    private static void menuClientes() {
        int op;
        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("0. Voltar");
            op = readInt("Escolha: ");
            switch (op) {
                case 1: cadastrarCliente(); break;
                case 2: listarClientes(); break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    private static void cadastrarCliente() {
        try {
            String nome = readLine("Nome: ");
            String doc = readLine("Documento (CPF): ");
            String data = readLine("Data de nascimento (yyyy-MM-dd | dd-MM-yyyy | dd/MM/yyyy): ");
            Cliente c = new Cliente(nome, doc, data);
            if (!c.isMaiorDeIdade()) {
                System.out.println("❌ Cliente menor de idade. Cadastro não permitido.");
                return;
            }
            clientes.add(c);
            System.out.println("✅ Cliente cadastrado: " + c);
        } catch (Exception e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }

    private static void listarClientes() {
        System.out.println("\n--- Lista de clientes ---");
        if (clientes.isEmpty()) System.out.println("[nenhum cliente cadastrado]");
        else clientes.forEach(System.out::println);
    }

    // ------------------ MENU PRODUTOS ------------------
    private static void menuProdutos() {
        int op;
        do {
            System.out.println("\n--- PRODUTOS ---");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Listar produtos");
            System.out.println("0. Voltar");
            op = readInt("Escolha: ");
            switch (op) {
                case 1: cadastrarProduto(); break;
                case 2: listarProdutos(); break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    private static void cadastrarProduto() {
        String nome = readLine("Nome do produto: ");
        double preco = readDouble("Preço: ");
        int estoque = readInt("Estoque: ");
        Produto p = new Produto(nome, preco, estoque);
        produtos.add(p);
        System.out.println("✅ Produto cadastrado: " + p);
    }

    private static void listarProdutos() {
        System.out.println("\n--- Lista de produtos ---");
        if (produtos.isEmpty()) System.out.println("[nenhum produto cadastrado]");
        else {
            for (int i = 0; i < produtos.size(); i++) {
                System.out.println(i + " - " + produtos.get(i));
            }
        }
    }

    // ------------------ MENU PEDIDOS ------------------
    private static void menuPedidos() {
        int op;
        do {
            System.out.println("\n--- PEDIDOS ---");
            System.out.println("1. Criar pedido");
            System.out.println("2. Gerenciar pedido existente");
            System.out.println("3. Listar pedidos");
            System.out.println("0. Voltar");
            op = readInt("Escolha: ");
            switch (op) {
                case 1: criarPedido(); break;
                case 2: gerenciarPedido(); break;
                case 3: listarPedidos(); break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    private static void criarPedido() {
        if (clientes.isEmpty()) {
            System.out.println("❌ Cadastre clientes antes de criar pedidos.");
            return;
        }
        if (produtos.isEmpty()) {
            System.out.println("❌ Cadastre produtos antes de criar pedidos.");
            return;
        }

        listarClientes();
        int idxCliente = readInt("Escolha índice do cliente: ");
        if (idxCliente < 0 || idxCliente >= clientes.size()) {
            System.out.println("Índice de cliente inválido.");
            return;
        }
        Cliente cliente = clientes.get(idxCliente);
        Pedido pedido = new Pedido(cliente);

        while (true) {
            listarProdutos();
            System.out.println("Digite -1 para encerrar adição de itens.");
            int idxProd = readInt("Escolha índice do produto: ");
            if (idxProd == -1) break;
            if (idxProd < 0 || idxProd >= produtos.size()) {
                System.out.println("Índice de produto inválido.");
                continue;
            }
            Produto prod = produtos.get(idxProd);
            int qtd = readInt("Quantidade: ");
            double precoVenda = readDouble("Preço de venda (pode ser diferente do preço cadastrado): ");
            pedido.adicionarItem(new ItemPedido(prod, qtd, precoVenda));
        }

        pedidos.add(pedido);
        System.out.println("✅ Pedido criado: " + pedido);
    }

    private static void listarPedidos() {
        System.out.println("\n--- Lista de pedidos ---");
        if (pedidos.isEmpty()) System.out.println("[nenhum pedido]");
        else for (int i = 0; i < pedidos.size(); i++) System.out.println(i + " - " + pedidos.get(i));
    }

    private static void gerenciarPedido() {
        if (pedidos.isEmpty()) {
            System.out.println("❌ Nenhum pedido cadastrado.");
            return;
        }
        listarPedidos();
        int idxPedido = readInt("Escolha índice do pedido: ");
        if (idxPedido < 0 || idxPedido >= pedidos.size()) {
            System.out.println("Índice de pedido inválido.");
            return;
        }
        Pedido pedido = pedidos.get(idxPedido);

        int op;
        do {
            System.out.println("\n-- Gerenciar Pedido --");
            System.out.println("Pedido: " + pedido);
            System.out.println("1. Listar itens");
            System.out.println("2. Adicionar item");
            System.out.println("3. Remover item");
            System.out.println("4. Finalizar pedido");
            System.out.println("5. Pagar pedido");
            System.out.println("6. Entregar pedido");
            System.out.println("0. Voltar");
            op = readInt("Escolha: ");
            switch (op) {
                case 1: pedido.listarItens(); break;
                case 2:
                    listarProdutos();
                    int idxP = readInt("Escolha índice do produto: ");
                    if (idxP < 0 || idxP >= produtos.size()) {
                        System.out.println("Índice produto inválido."); break;
                    }
                    Produto p = produtos.get(idxP);
                    int qtd = readInt("Quantidade: ");
                    double preco = readDouble("Preço de venda: ");
                    pedido.adicionarItem(new ItemPedido(p, qtd, preco));
                    break;
                case 3:
                    pedido.listarItens();
                    int idxI = readInt("Índice do item a remover: ");
                    pedido.removerItem(idxI);
                    break;
                case 4: pedido.finalizar(); break;
                case 5: pedido.pagar(); break;
                case 6: pedido.entregar(); break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    // ------------------ CASO DE USO FUNCIONAL ------------------
    private static void casoDeUsoFuncional() {
        System.out.println("\n=== CASO DE USO FUNCIONAL (demo) ===");
        Cliente demoCliente = new Cliente("Nelson Herrera", "12345678910", "1975-02-24");
        Produto demo1 = new Produto("Notebook", 3500.0, 5);
        Produto demo2 = new Produto("Mouse", 80.0, 20);
        produtos.add(demo1); produtos.add(demo2);
        clientes.add(demoCliente);

        Pedido pedido = new Pedido(demoCliente);

        // Streams + Optionals: primeiro produto > 1000 e primeiro <= 1000
        Optional.ofNullable(produtos.stream().filter(pr -> pr.getPreco() > 1000).findFirst().orElse(null))
                .ifPresent(pr -> pedido.adicionarItem(new ItemPedido(pr, 1, pr.getPreco() - 100)));

        Optional.ofNullable(produtos.stream().filter(pr -> pr.getPreco() <= 1000).findFirst().orElse(null))
                .ifPresent(pr -> pedido.adicionarItem(new ItemPedido(pr, 2, pr.getPreco())));

        pedido.listarItens();
        pedido.finalizar();
        pedido.pagar();
        pedido.entregar();

        pedidos.add(pedido);
        System.out.println("Demo finalizada. Pedido: " + pedido);
    }
}
