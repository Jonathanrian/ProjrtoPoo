import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConection {
   public static void main(String args[]) {
      String jdbcURL = "jdbc:postgresql://localhost:5432/usuario";
      String username = "postgres";
      String password = "123";
      
      try{
          Connection connection = DriverManager.getConnection(jdbcURL, username, password);
          System.out.println("Conexão bem sucedida!");

          connection.close();
      } catch (SQLException e) {
         System.out.println("Falha ao conectar ao banco de dados :(");
         e.printStackTrace();
      }

   }      
      
}