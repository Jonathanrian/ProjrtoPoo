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
    
    // encapsulamento dos atributos
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String telefone;
    private String usuario;
    private String senha;
    private String endereco;
    private Date dataNasc;

    // construtor da classe Usuario
    public Usuario(String nomeCompleto, String cpf, String email, String telefone, String usuario, String senha, String endereco, Date dataNasc) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.usuario = usuario;
        this.senha = senha;
        this.endereco = endereco;
        this.dataNasc = dataNasc;
    }

    // gets e sets para fazer o acesso dos atributos dos quais foram encapsulados
    public Date getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    // método para fazer a verificação dos dados inseridos pelo usuario antes de se 
    // colocar no banco de dados
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

    // em todos os métodos dos quais retorna um boolean para o ValidarDados()
    // foi utilizado regex para fazer a verificação dos caracteres inseridos
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
        public static boolean login(String usuario, String senha){
            //tenta realizar a conexão com o banco
            try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/usuario", "postgres", "123");

            //pesquisa na tabela usuario e pela coluna usuario se 
            // há um usuario que faça um match com o usuario e senha contidos dentro do banco
            String query = "SELECT * FROM usuario WHERE usuario = ? AND senha = ?"; //consulta o banco
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, usuario);
            statement.setString(2, senha); // inserindo as informações na consulta nos ?

            ResultSet resultSet = statement.executeQuery(); // executando a consulta

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

        // método para recuperar a senha
    public boolean recuperarSenha(String nomeUsuario, String novaSenha){
        try {
            // conectando com o banco de dados
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/usuario", "postgres", "123");

            String sql = "UPDATE usuario SET senha = ? WHERE usuario = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, novaSenha);
            stmt.setString(2, nomeUsuario);
            

            int rowsUpdated = stmt.executeUpdate();

            // verifica se alguma linha foi alterada, para verificar se a alteração
            // foi feita com sucesso
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


    public boolean cadastrar(String nome, String email, String cpf, String telefone, String usuario, String senha, String endereco, String data_nasc){
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

            try {
                // Fazendo a conversão do tipo String para Date, para inserir no banco de dados
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date date = sdf.parse(data_nasc);
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    
                stmt.setDate(8, sqlDate);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Falha ao converter data");
                return false;
            }
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

    public boolean editarInformacoes(String usuario, String novoNome, String novoEmail, String novoCpf, String novoTelefone, String novoUsuario, String novaSenha, String novoEndereco, String novaDataNasc){
        try{
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/usuario", "postgres", "123");

            String query = "UPDATE usuario SET nome_completo = ?, email = ?, telefone = ?, cpf = ?, usuario = ?, senha = ?, endereco = ?, data_nasc = ? WHERE usuario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, novoNome);
            statement.setString(2, novoEmail);
            statement.setString(3, novoTelefone);
            statement.setString(4, novoCpf);
            statement.setString(5, novoUsuario);
            statement.setString(6, novaSenha);
            statement.setString(7, novoEndereco);
            statement.setString(8, novaDataNasc);
            statement.setString(9, usuario);

            int linhasAfetadas = statement.executeUpdate();

            statement.close();
            connection.close();

            if (linhasAfetadas > 0) {
                System.out.println("Informações atualizadas com sucesso.");
                return true;
            } else {
                System.out.println("Nenhum usuário encontrado com o usuario fornecido.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirUsuario(String usuario) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/usuario", "postgres", "123");

            String sql = "DELETE FROM usuario WHERE usuario = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario);

            int rowsDeleted = stmt.executeUpdate();
            stmt.close();
            connection.close();

            if (rowsDeleted > 0) {
                System.out.println("Usuário excluído com sucesso");
                return true;
            } else {
                System.out.println("Usuário não encontrado");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Falha ao excluir usuário");
            return false;
        }
    }


}

