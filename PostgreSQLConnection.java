import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnection {
    private static PostgreSQLConnection instance; // Instancia de Banco de Dados
    private Connection connection; // Conectar o Banco 

    private final String url = "jdbc:postgresql://localhost:5432/Projeto_Poo"; // Url do Banco
    private final String username = "postgres"; // Username do Banco
    private final String password = "123"; // Senha do Banco

    // Cria uma conexão com o Banco de Dados
    private PostgreSQLConnection() {
        try { // Tenta Conectar
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) { // Se não conseguir da erro
            System.err.println("Error ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    public static synchronized PostgreSQLConnection getInstance() {
        if (instance == null) {
            instance = new PostgreSQLConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexão fechada com sucesso.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
