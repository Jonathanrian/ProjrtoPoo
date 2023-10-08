import java.sql.*;
import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nome_completo;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;
    private String usuario;
    private String endereco;
    private LocalDate dataNasc;
    
    public Usuario(String nome_completo, String cpf, String email, String telefone, String senha,
        String usuario, String endereco, LocalDate dataNasc) {
        this.nome_completo = nome_completo;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.usuario = usuario;
        this.endereco = endereco;
        this.dataNasc = dataNasc;
    }
    
    public boolean cadastrar(){
        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection(); //conectando com o banco

            if (this.validarDados()) {

                PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM usuario WHERE cpf = ?");

                pstmt.setString(1, cpf);
                ResultSet rs = pstmt.executeQuery();

                if (!rs.next()) {
                    pstmt = connection.prepareStatement(
                        "INSERT INTO " 
                      + "usuario(nomecompleto, cpf, email, telefone, usuario, senha, endereco, dataNasc)" 
                      + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

                    pstmt.setString(1, getNome_completo());
                    pstmt.setString(2, getCpf());
                    pstmt.setString(3, getEmail());
                    pstmt.setString(4, getTelefone());
                    pstmt.setString(5, getusuario());
                    pstmt.setString(6, getSenha());
                    pstmt.setString(7, getEndereco());
                    pstmt.setDate(8, Date.valueOf(dataNasc));
                    pstmt.executeUpdate();

                    return true;
                }
              
                System.out.println("erro ao cadastrar");
                return false;
                
            }
            
            return false;

        } catch (Exception e) {
            System.out.println("Aconteceu um erro cadastrar usuario!");
            return false;
        }
    }

    public static boolean login(String usuario, String senha){
        try {
            Connection connection = PostgreSQLConnection.getInstance().getConnection(); //conecta com o banco

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM usuario WHERE usuario = ? AND senha = ?"); // faznd consulta
            pstmt.setString(1, usuario);
            pstmt.setString(2, senha);

            ResultSet rs = pstmt.executeQuery();

            // Verificar se encontrou um usuário com o usuário e senha fornecidos
            if (rs.next()) {
                // Autenticação bem-sucedida
                System.out.println("Login ok:");
                return true;
            } else {
                
                System.out.println("Suas informações não correspodem, tente novamente ou escolha outra opção! ");
                return false;
            }

        }catch (Exception e) {
            // Autenticação falhou (usuário ou senha incorretos)
            e.printStackTrace(); // Trate melhor as exceções em um ambiente de produção
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

    public boolean editarInformacoes(int novo_Id, String usuario, String novoNome, String novoEmail, String novoCpf, String novoTelefone, String novoUsuarios, String novaSenha, String novoEndereco) {
        try {
            Connection connection = PostgreSQLConnection.getInstance().getConnection();
            
            // Verifique se o novo usuário não existe
            PreparedStatement verificaUsuarioExistente = connection.prepareStatement("SELECT COUNT(*) FROM usuario WHERE usuario = ?");
            verificaUsuarioExistente.setString(1, novoUsuarios);
            ResultSet resultado = verificaUsuarioExistente.executeQuery();
            resultado.next();
            int count = resultado.getInt(1);
            
            if (count > 0) {
                System.out.println("O novo usuário já existe. Não é possível atualizar.");
                return false;
            }
    
            // Atualize as informações do usuário existente
            PreparedStatement pstmtAtualizacao = connection.prepareStatement("UPDATE usuario SET id_usuario = ?, nomecompleto = ?, cpf = ?, email = ?, telefone = ?, usuario = ?, senha = ?, endereco = ? WHERE usuario = ?");
            pstmtAtualizacao.setInt(1, novo_Id);
            pstmtAtualizacao.setString(2, novoNome);
            pstmtAtualizacao.setString(3, novoCpf);
            pstmtAtualizacao.setString(4, novoEmail);
            pstmtAtualizacao.setString(5, novoTelefone);
            pstmtAtualizacao.setString(6, novoUsuarios);
            pstmtAtualizacao.setString(7, novaSenha);
            pstmtAtualizacao.setString(8, novoEndereco);
            pstmtAtualizacao.setString(9, usuario);
    
            int linhasAfetadas = pstmtAtualizacao.executeUpdate();
    
            if (linhasAfetadas > 0) {
                System.out.println("Informações atualizadas com sucesso.");
    
                // Exclua o registro anterior
                PreparedStatement pstmtExclusao = connection.prepareStatement("DELETE FROM usuario WHERE usuario = ?");
                pstmtExclusao.setString(1, usuario);
                pstmtExclusao.executeUpdate();
    
                System.out.println("Registro anterior excluído.");
                
                return true;
            } else {
                System.out.println("Nenhum usuário encontrado com o usuário fornecido.");
                return false;
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean recuperarSenha(String nomeUsuario, String novaSenha){
        try {
            Connection connection = PostgreSQLConnection.getInstance().getConnection(); 
            PreparedStatement pstmt = connection.prepareStatement("UPDATE usuario SET senha = ? WHERE usuario = ?");

            pstmt.setString(1, novaSenha);
            pstmt.setString(2, nomeUsuario);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Senha alterada com sucesso!");
                return true;
            } else {
                System.out.println("Usuário não encontrado.");
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirusuario(String usuario){
        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection(); //conectando ao banco de bados 
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM usuario WHERE cpf = ?");
            pstmt.setString(1, getCpf());

            int rowCount = pstmt.executeUpdate();

            if(rowCount > 0){
                System.out.println("Você excluio um usuario!");
                return true;
            }
            else{
                return false; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao excluir ");
            return false;
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome_completo() {
        return nome_completo;
    }
    public void setNome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
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

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getusuario() {
        return usuario;
    }
    public void setusuario(String usuario) {
        this.usuario = usuario;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getEndereco() {
        return endereco;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

   
}