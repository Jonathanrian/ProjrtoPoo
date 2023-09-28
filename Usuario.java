import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class Usuario {

    private String nomeCompleto;
    private String cpf;
    private String email;
    private String telefone;
    private String usuario;
    private String senha;
    //private Endereco endereco;
    private Date dataNasc;

    public Usuario(String nomeCompleto, String cpf, String email, String telefone, String usuario, String senha, Date dataNasc) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.usuario = usuario;
        this.senha = senha;
        this.dataNasc = dataNasc;
    }

    public Date getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
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


    public static boolean validarDados(String nomeCompleto, String email, String cpf,
                                             String telefone, String usuario, String senha,
                                             String endereco, String dataNasc) {
        return validarNomeCompleto(nomeCompleto) &&
                validarEmail(email) &&
                validarCPF(cpf) &&
                validarTelefone(telefone) &&
                validarUsuario(usuario) &&
                validarSenha(senha) &&
                validarEndereco(endereco) &&
                validardataNasc(dataNasc);
    }

    public static boolean validarNomeCompleto(String nomeCompleto) {
        return nomeCompleto.matches("[a-zA-Z\\s]+");
    }

    public static boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validarCPF(String cpf) {
        return cpf.matches("\\d{11}");
    }

    public static boolean validarTelefone(String telefone) {
        return telefone.matches("\\d{8,11}");
    }

    public static boolean validarUsuario(String usuario) {
        return usuario.matches("[a-zA-Z0-9]+");
    }

    public static boolean validarSenha(String senha) {
        return senha.length() >= 8;
    }

    public static boolean validarEndereco(String endereco) {
        return !endereco.isEmpty();
    }

    @SuppressWarnings("unused")
    public static boolean validardataNasc(String dataNasc) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            Date data = dateFormat.parse(dataNasc);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


        // método para realizar o login do usuario consultando o banco de dados
        public boolean Login(String usuario, String senha){
            //tenta realizar a consexão 
            try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/usuario", "postgres", "123");

            //pesquisa no banco na tabela usuario e pela coluna usuario se 
            // há um usuario que faça um match com o usuario e senha contidos dentro do banco
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

