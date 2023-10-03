import java.util.Date;


public class Pedido {
    private int numPedido;
    //private list <Pedido> produtosCarrinho;
    private String status;
    private Usuario usuario;
    private Date dataCriacao;
    private float valorTotal;
    private String formaPagamento;
    private String endereco;


    public Pedido(int numPedido, String status, Usuario usuario, Date dataCriacao, float valorTotal, String formaPagamento, String endereco) {
        this.numPedido = numPedido;
        this.status = status;
        this.usuario = usuario;
        this.dataCriacao = dataCriacao;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.endereco = endereco;
    }


    public int getNumPedido() {
        return numPedido;
    }
    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Date getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public float getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
    public String getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    
    
    


}
