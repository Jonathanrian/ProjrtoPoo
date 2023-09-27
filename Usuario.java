public class Usuario {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String telefone;
    private String usuario;
    private String senha;
    //private Endereco endereco;
    //private Date dataNasc;

    public Usuario(String nomeCompleto, String cpf, String email, String telefone, String usuario, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean Login(String usuario, String senha){
        if(this.usuario == usuario && this.senha == senha){
            return true;            
        }else{
            return false;
        }
    }

    public boolean RecuperarSenha(String usuario, String novaSenha){
        if(this.usuario == usuario){
            System.out.println("A nova senha foi alterada com sucesso");
            senha = novaSenha;
            return true;
        }else{
            System.out.println("Usuario não cadastrado");
            return false;
        }
    }

}   
