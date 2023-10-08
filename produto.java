import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class produto {
    private int id;
    private String nomeProduto;
    private double precoProduto;
    private String categoria;
    private String fabricante;
    private boolean emEstoque;
    private String descricao;
    private double desconto;
    
    public produto(int id, String nomeProduto, double precoProduto, String categoria, String fabricante,
        boolean emEstoque, String descricao, double desconto) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.categoria = categoria;
        this.fabricante = fabricante;
        this.emEstoque = emEstoque;
        this.descricao = descricao;
        this.desconto = desconto;
    }

    public boolean cadastrarProduto(String nomeproduto, double precoproduto, String categoria, String descricao, String fabricante) {

        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection(); //conectando ao banco 

            if (this.validarDados()) {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM produto WHERE nomeproduto = ?");
            pstmt.setString(1, nomeproduto);
            ResultSet rs = pstmt.executeQuery();

                if (!rs.next()) {
                    pstmt = connection.prepareStatement(
                    "INSERT INTO produto (nomeproduto, precoproduto, categoria, descricao, fabricante) VALUES (?, ?::double precision, ?, ?, ?)");
                    
                    pstmt.setString(1, nomeproduto);
                    pstmt.setDouble(2, precoproduto);
                    pstmt.setString(3, categoria);
                    pstmt.setString(4, descricao);
                    pstmt.setString(5, fabricante);
                    pstmt.executeUpdate();

                    return true;
                }
            
                return false;
            }
            
            System.out.println("deu bomm");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarPrecoProduto(String nome, double novoPreco) {

        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection(); //conectando ao banco 

            String sql = "UPDATE produto SET preco = ? WHERE nomeproduto = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDouble(1, novoPreco);
            statement.setString(2, nome);

            statement.executeUpdate();

            statement.close();
            
            System.out.println("Valor do produto atualizado! ");
            return true;
        } catch (SQLException e) {

            System.out.println("Erro ao atualizar o produto!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean ExcluirProduto(String nomeproduto) {
        try {
            Connection connection = PostgreSQLConnection.getInstance().getConnection(); //conectando ao banco
    
            String sql = "DELETE FROM produto WHERE nomeproduto = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            // Defina o valor do parâmetro usando o índice 1
            stmt.setString(1, nomeproduto);
    
            int rowsDeleted = stmt.executeUpdate();
            stmt.close();
    
            if (rowsDeleted > 0) {
                System.out.println("Produto excluído com sucesso");
                return true;
            } else {
                System.out.println("Produto não encontrado");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Falha ao excluir Produto");
            return false;
        }
    
    }

    public static boolean exibirDetalhes() {
        try {
            Connection connection = PostgreSQLConnection.getInstance().getConnection(); //conectando ao banco

            String sql = "SELECT * FROM produto";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String nomeproduto = resultSet.getString("nomeproduto");
                double precoproduto = resultSet.getDouble("precoproduto");
                String fabricante = resultSet.getString("fabricante");

                System.out.println("Nome do Produto: " + nomeproduto + "\n" + "PreçoProduto: " + precoproduto +"\n"+ "Fabricante: " + fabricante + "\n");
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Falha ao mostrar produtos");
            return false;
        }
    }

    public boolean validarDados(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getFabricante() {
        return fabricante;
    }

    public boolean isEmEstoque() {
        return emEstoque;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public void setEmEstoque(boolean emEstoque) {
        this.emEstoque = emEstoque;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

}
