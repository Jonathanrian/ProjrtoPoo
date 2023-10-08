public class ItemPedido {
    private int idProduto;
    private String nomeProduto;
    private double preco;
    private static int quantidade;


    public ItemPedido(int idProduto, String nomeProduto, double preco, int quantidade) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public double getPreco() {
        return preco;
    }

    public static int getQuantidade() {
        return quantidade;
    }


    @Override
    public String toString() {
        return "idProduto: " + idProduto + ", nomeProduto: " + nomeProduto + ", preco: " + preco
        + ", quantidade: " + quantidade;
    }

    
}
