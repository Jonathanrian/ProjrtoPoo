import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Pedido {
    private int id_pedido;
    private String status;
    private Usuario usuario;
    private Date dataCriacao;
    private float valorTotal;
    private String formaPagamento;
    private List<ItemPedido> itens;


    public Pedido(int id_pedido, String status, Usuario usuario, Date dataCriacao, float valorTotal,
        String formaPagamento) {
        this.id_pedido = id_pedido;
        this.status = status;
        this.usuario = usuario;
        this.dataCriacao = (dataCriacao != null) ? dataCriacao : new Date();
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.itens = new ArrayList<>();
    }

    public boolean catalogoProdutos() {
        try {
            Connection connection = PostgreSQLConnection.getInstance().getConnection(); //conectando com o banco

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("======== Catálogo de Produtos ========");
                System.out.println("Escolha uma opção:");
                System.out.println("1. Listar produtos");
                System.out.println("2. Adicionar item ao pedido");
                System.out.println("3. Calcular total do pedido");
                System.out.println("4. Realizar pedido");
                System.out.println("5. Sair");
                System.out.print("Opção: ");

                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        listarProdutos(connection);
                        break;
                    case 2:
                        adicionarItemAoPedido(connection, scanner);
                        break;
                    case 3:
                        calcularTotalDoPedido();
                        break;
                    case 4:
                        realizarPedido(connection);
                        break;
                    case 5:
                        System.out.println("Saindo do programa.");
                        connection.close();
                        return true;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro na conexão com o banco de dados.");
            return false;
        }
    }

    private void listarProdutos(Connection connection) throws SQLException {
        String sql = "SELECT * FROM produto";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet resultado = pstmt.executeQuery();

        System.out.println("======== Lista de Produtos ========");

        while (resultado.next()) {
            int id = resultado.getInt("id_produto");
            String nome = resultado.getString("nomeproduto");
            double preco = resultado.getDouble("precoproduto");
            System.out.println(id + ". " + nome + " - R$" + preco);
        }
    }

    private void adicionarItemAoPedido(Connection connection, Scanner scanner) throws SQLException {
        listarProdutos(connection);
        System.out.print("Digite o ID do produto que deseja adicionar ao pedido: ");
        int idProduto = scanner.nextInt();

        // Recuperar informações do produto selecionado
        String sql = "SELECT * FROM produto WHERE id_produto = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, idProduto);
        ResultSet resultado = pstmt.executeQuery();

        if (resultado.next()) {
            int id = resultado.getInt("id_produto");
            String nome = resultado.getString("nomeproduto");
            double preco = resultado.getDouble("precoproduto");

            System.out.print("Digite a quantidade desejada: ");
            int quantidade = scanner.nextInt();
            
            // Criar um objeto ItemPedido com base no produto selecionado
            ItemPedido item = new ItemPedido(id, nome, preco, quantidade);
            itens.add(item);

            System.out.println("Item adicionado ao pedido: " + item);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void calcularTotalDoPedido() {
        float total = 0;
        for (ItemPedido item : itens) {
            total += item.getPreco() * ItemPedido.getQuantidade();
        }
        valorTotal = total;
        System.out.println("Total do Pedido: R$" + total);
    }

    private void realizarPedido(Connection connection) throws SQLException {
        if (itens.isEmpty()) {
            System.out.println("O pedido está vazio. Adicione itens antes de realizar o pedido.");
            return;
        }
    
        try {
            // 1. Inserir o pedido na tabela de pedidos
            String inserirPedidoSQL = "INSERT INTO pedido (status, id_usuario, data_criacao, valor_total, forma_pagamento) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmtPedido = connection.prepareStatement(inserirPedidoSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmtPedido.setString(1, status);
            pstmtPedido.setInt(2, usuario.getId()); // Suponha que você tenha um método getId() em sua classe Usuario
            pstmtPedido.setDate(3, new java.sql.Date(dataCriacao.getTime()));
            pstmtPedido.setFloat(4, valorTotal);
            pstmtPedido.setString(5, formaPagamento);
            
            pstmtPedido.executeUpdate();
    
            // Recuperar o ID gerado para o pedido inserido
            ResultSet generatedKeys = pstmtPedido.getGeneratedKeys();
            int pedidoId = -1;
            if (generatedKeys.next()) {
                pedidoId = generatedKeys.getInt(1);
            }
    
            // 2. Inserir os itens do pedido na tabela de itens do pedido
            String inserirItemPedidoSQL = "INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmtItemPedido = connection.prepareStatement(inserirItemPedidoSQL);
    
            for (ItemPedido item : itens) {
                pstmtItemPedido.setInt(1, pedidoId);
                pstmtItemPedido.setInt(2, item.getIdProduto());
                pstmtItemPedido.setInt(3, item.getQuantidade()); 
                pstmtItemPedido.setDouble(4, item.getPreco());
    
                pstmtItemPedido.executeUpdate();
            }
    
            System.out.println("Pedido realizado com sucesso!");
            itens.clear(); // Limpar a lista de itens após a realização do pedido
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao realizar o pedido.");
        }
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public String getStatus() {
        return status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

}
