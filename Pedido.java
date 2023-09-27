public class Pedido {
    private int numPedido;
    //private list <Pedido> produtosCarrinho;
    private String status;
    private Usuario usuario;
    //private Date dataCriacao;
    private float valorTotal;
    private String formaPagamento;
    //private Endereco endereco;

    public Pedido(int numPedido, String status, Usuario usuario, float valorTotal, String formaPagamento) {
        this.numPedido = numPedido;
        this.status = status;
        this.usuario = usuario;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
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

    


}
