package view;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import controler.*;
import module.*;
public class Main {
    public static void main(String[] args) {
        int op;
        Usuario usuario;
        String aux;
        boolean flag;
        UsuarioDao usuarioDao = new UsuarioDao();
        Scanner teclado = new Scanner(System.in);
        do{
            System.out.println("LOGIN - SMAS");
            System.out.print("1-Logar\n2-Cadastrar-se\n3-Esqueceu senha\n4-Sair:");
            op = teclado.nextInt();
            switch (op){
                case 1:
                    usuario = new Usuario();
                    System.out.print("Digite o email: ");
                    usuario.setEmail(teclado.next());
                    System.out.print("\nDigite a senha: ");
                    usuario.setSenha(teclado.next());
                    if(usuarioDao.selectEmail(usuario.getEmail()) != null){
                        if(usuario.getSenha().equals(usuarioDao.selectEmail(usuario.getEmail()).getSenha())){
                            usuario = usuarioDao.selectEmail(usuario.getEmail());
                            System.out.println(usuario.getNome() + " efetuou o login com Sucesso!");
                        }else{
                            System.out.println("Email ou senha invalido.Tente novamente!\n");
                        }
                    }else{
                        System.out.println("Email ou senha invalido.Tente novamente!\n");
                    }
                    break;
                case 2:
                    Municipio m = new Municipio();
                    MunicipioDao mDao = new MunicipioDao();
                    usuario = new Usuario();
                    teclado.nextLine();
                    System.out.print("\nDigite o seu nome: ");
                    usuario.setNome(teclado.nextLine());
                    System.out.print("\nDigite o seu email: ");
                    usuario.setEmail(teclado.next());
                    do{
                        flag = true;
                        System.out.print("\nDigite a sua senha: ");
                        aux = teclado.next();
                        System.out.print("\nDigite a sua senha novamente: ");
                        if(!(aux.equals(teclado.next()))){
                            System.out.println("Senhas distintas, por favor tente novamente!");
                            flag = false;
                        }
                    }while(!flag);
                    usuario.setSenha(aux);
                    teclado.nextLine();
                    do{
                        flag = true;
                        System.out.print("\nDigite o nome do municipio: ");
                        m.setNome(teclado.nextLine());
                        m.setUf("RN");
                        if(mDao.selectNameAndUf(m.getNome(),m.getUf()) == null){
                            System.out.println("\nMunicipio Invalido!");
                            flag = false;
                        }
                    }while(!flag);
                    m = mDao.selectNameAndUf(m.getNome(),m.getUf());
                    usuario.setIdMunicipio(m.getId());
                    usuarioDao.insert(usuario);
                    System.out.println("Usuario cadastrado com sucesso!");
                    break;
                case 3:
                    flag = true;
                    System.out.print("Digite o email do usuario que deseja alterar a senha: ");
                    usuario = usuarioDao.selectEmail(teclado.next());
                    if(usuario != null){
                        do{
                            System.out.print("\nDigite a sua nova senha: ");
                            aux = teclado.next();
                            System.out.print("\nDigite a sua nova senha novamente: ");
                            if(!(aux.equals(teclado.next()))){
                                System.out.println("Senhas distintas, por favor tente novamente!");
                                flag = false;
                            }
                        }while(!flag);
                        usuario.setSenha(aux);
                        usuarioDao.update(usuario.getEmail(), usuario);
                        System.out.println("Senha alterada com sucesso!");
                    }else{
                        System.out.println("email invalido!");
                    }
                    break;
                case 4:
                    System.out.println("Fim do Programa!");
                    break;
                default:
                    System.out.println("Opção Invalida!");
            }
        }while(op != 4);
    }
}