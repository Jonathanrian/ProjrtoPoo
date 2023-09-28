import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    // public boolean Login(String usuario, String senha){
    //     if(this.usuario == usuario && this.senha == senha){
    //         return true;            
    //     }else{
    //         return false;
    //     }
    // }

        public boolean Login( String usuario, String senha){
            try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/usuario", "postgres", "123");

            String query = "SELECT * FROM usuario WHERE usuario = ? AND senha = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, usuario);
            statement.setString(2, senha);

            ResultSet resultSet = statement.executeQuery();

            boolean sucesso = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();

            return sucesso;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        }


    public boolean RecuperarSenha(String nomeUsuario, String novaSenha){
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/usuario", "postgres", "123");

            String sql = "UPDATE usuario SET senha = ? WHERE usuario = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, novaSenha);
            stmt.setString(2, nomeUsuario);
            

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Senha alterada com sucesso!");
                connection.close();
                stmt.close();
                return true;
            } else {
                System.out.println("Usuário não encontrado.");
                connection.close();
                stmt.close();
                return false;
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean Cadastrar(String nome, String email, String cpf, String telefone, String usuario, String senha, String endereco, String data_nasc){
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/usuario", "postgres", "123");

            String sql = "INSERT INTO usuario (nome_completo, email, cpf, telefone, usuario, senha, endereco, data_nasc) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, cpf);
            stmt.setString(4, telefone);
            stmt.setString(5, usuario);
            stmt.setString(6, senha);
            stmt.setString(7, endereco);
            stmt.setString(8, data_nasc);
            stmt.executeUpdate();

            stmt.close();
            connection.close();
            System.out.println("Usuario cadastrado com sucesso");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Falha ao cadastrar usuario");
            return false;
        }
    }
    }

