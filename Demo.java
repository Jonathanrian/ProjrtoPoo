import java.time.LocalDate;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        telaHome tela = new telaHome();
        tela.home();

        String login = "";
        String senha = "";
        
        LocalDate data = LocalDate.of(2003, 4, 2);
        usuario cliente = new usuario("Antonio David", "11122233312", "antonioDavid@gmail.com", "88998545677", "david123", "david1", "Rua padre daniel", data);

        cliente.excluirusuario("david123");


        //cliente.recuperarSenha("ryan123", "123ryan"); 

        //--------------------------------------------------------------------------------------------------------------------------------

        produto prod = new produto(1, "faixa", 10.0, "limpeza", "ryanLTDA", true, "o melhor do", 1.0);
        prod.cadastrarProduto("sabão", 9.50, "limpeza", "o melhor do mercado", "marilux");

        //System.out.println(prod.exibirDetalhes());
        //prod.ExcluirProduto("sabao");


        //-------------------------------------------------------------------------------------------------------
        Scanner ent = new Scanner(System.in);
        int opcao;
        do {

            System.out.println("\nBem-vindo ao Menu de Boas-Vindas!");
            System.out.println("1. Fazer Login");
            System.out.println("2. Produtos");
            System.out.println("3 Editar suas informações");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = ent.nextInt();

            switch (opcao){
            case 1:
                
                try {

                    System.out.print("\nInforme seu usuario: ");
                    login = ent.next();
                    System.out.print("Senha: ");
                    senha = ent.next();

                    usuario.login(login, senha);
                      
                } catch (Exception e) {
                    e.printStackTrace(); 
                }
                
                break;
            case 2:
                System.out.println("Você escolheu a Opção 2.");
                Pedido pedido = new Pedido(opcao, login, cliente, null, opcao, senha);
                pedido.catalogoProdutos();
                break;
            case 3:
                int id = 4;

                String usuario;
                String novoNome; 
                String novoEmail;
                String novoCpf; 
                String novoTelefone;
                String novoUsuarios;
                String novaSenha;
                String novoEndereco;

                System.out.println("Nos informes as seguintes informações: " + "\n" );

                System.out.print("Qual seu usuario: ");
                usuario = ent.next();

                System.out.print("Seu novo nome: ");
                novoNome = ent.next();

                System.out.print("Seu novo email: ");
                novoEmail = ent.next();

                System.out.print("Seu novo cpf: ");
                novoCpf = ent.next();

                System.out.print("Seu novo Telenone: ");
                novoTelefone = ent.next();
                
                System.out.print("Seu novo Usuario: ");
                novoUsuarios = ent.next();

                System.out.print("Seu nova senha: ");
                novaSenha = ent.next();

                System.out.print("Seu novo Endereço: ");
                novoEndereco = ent.next();

                cliente.editarInformacoes(id, usuario, novoNome, novoEmail, novoCpf, novoTelefone, novoUsuarios, novaSenha, novoEndereco);
                

                break;

            case 4:
                System.out.println("Saindo do programa. Adeus!");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                break;
            }
            
        } while (opcao != 3);


        System.out.println(cliente.cadastrar());
        ent.close();
    }
}
