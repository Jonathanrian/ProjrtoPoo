
public class Demo {
   public static void main(String args[]) {
      Usuario usuario = new Usuario(null, null, null, null, null, null);
      System.out.println(usuario.Cadastrar("robertinho delas", "carlim@gmail.com", "1234123123", "9090", "roberts", "123", "rua sem saida", "teste"));
      usuario.RecuperarSenha("roberts", "456");

      

   }      
      
}