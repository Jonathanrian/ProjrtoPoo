
public class Demo {
   public static void main(String args[]) {
      Usuario usuario = new Usuario(null, null, null, null, null, null, null, null);
      
      //System.out.println(usuario.Cadastrar("robertinho delas", "carlim@gmail.com", "1234123123", "9090", "roberts", "123", "rua sem saida", "teste"));
      //System.out.println(usuario.RecuperarSenha("roberts", "456"));
      //System.out.println(usuario.Login("roberts", "456"));

      // String nomeCompleto = "Fica frio ai";
      // String email = "gelado@incriveis.com";
      // String cpf = "12345678901";
      // String telefone = "123456789";
      // String usuario2 = "gelado123";
      // //senha tem que ter pelo menos 8 digitos faz parte da verificação do regex
      // String senha = "ice12345";
      // String endereco = "Rua incriveis, 123";
      // String dataNascimento = "07/12/2002";

      // if(Usuario.ValidarDados(nomeCompleto, email, cpf, telefone, usuario2, senha, endereco, dataNascimento)){
      //    System.out.println("Dados validados");
      //    System.out.println(usuario.Cadastrar(nomeCompleto, email, cpf, telefone, usuario2, senha, endereco, dataNascimento));
      // }else {
      //    System.out.println("Alguns dados estão inválidos :(");
      // }

      usuario.ExcluirUsuario("gelado123");

      //usuario.EditarInformacoes("teste", "sucessao de sucessos", email, cpf, telefone, "gelado", senha, endereco, dataNascimento);

   }      
      
}