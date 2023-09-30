import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Produto {
    private String nome;
    private double preco;
    private String categoria;
    private boolean emEstoque;
    private String descricao;
    private String fabricante;
    private double desconto;
    //private Imagem imagem;

    public Produto(String nome, double preco, String categoria, boolean emEstoque, String descricao, String fabricante,
            float desconto) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.emEstoque = emEstoque;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.desconto = desconto;
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public boolean isEmEstoque() {
        return emEstoque;
    }
    public void setEmEstoque(boolean emEstoque) {
        this.emEstoque = emEstoque;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getFabricante() {
        return fabricante;
    }
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    public double getDesconto() {
        return desconto;
    }
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }



    public boolean cadastrarProduto(String nome, double preco) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/produto", "postgres", "123");

            String sql = "INSERT INTO produto (nome, preco) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, nome);
            statement.setDouble(2, preco);

            statement.executeUpdate();

            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarPrecoProduto(String nome, double novoPreco) {
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/produto", "postgres", "123");
            String sql = "UPDATE produtos SET preco = ? WHERE nome = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDouble(1, novoPreco);
            statement.setString(2, nome);

            statement.executeUpdate();

            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
