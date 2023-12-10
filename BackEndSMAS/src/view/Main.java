package view;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import controler.*;
import module.*;
import org.w3c.dom.ls.LSOutput;

public class Main {
    public static void main(String[] args) {
        int op;
        Alerta alerta;
        MunicipioDao municipioDao = new MunicipioDao();
        Especie especie;
        EspecieDao especieDao = new EspecieDao();
        MunicipioEspecie m;
        MunicipioEspecieDao mDao = new MunicipioEspecieDao();
        AlertaDao alertaDao = new AlertaDao();
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
                            do{
                                System.out.println("1-Alertas");
                                if(usuario.getFuncao() > 1)
                                    System.out.println("2-Especies");
                                if(usuario.getFuncao() > 2)
                                    System.out.println("3-Usuarios\n4-Municipios");
                                System.out.print("0-Sair: ");
                                op = teclado.nextInt();
                                switch (op){
                                    case 1:
                                        alerta = new Alerta();
                                        System.out.print("1-Adicionar\n2-Editar\n3-Remover\n4-Listar\n0-Sair: ");
                                        op = teclado.nextInt();
                                        teclado.nextLine();
                                        switch (op){
                                            case 1:
                                                System.out.print("Digite a descrição: ");
                                                alerta.setDescricao(teclado.nextLine());
                                                System.out.print("\nDigite o id da especie: ");
                                                op = teclado.nextInt();
                                                if(especieDao.selectId(op) != null){
                                                    alerta.setIdEspecie(op);
                                                    alerta.setEmailUsuario(usuario.getEmail());
                                                    alertaDao.insert(alerta);
                                                    ArrayList<Alerta> lista = alertaDao.selectAll();
                                                    alerta = lista.get(lista.size() - 1);
                                                    m = new MunicipioEspecie(usuario.getIdMunicipio(), alerta.getIdEspecie(), alerta.getId());
                                                    mDao.insert(m);
                                                    System.out.println("Alerta cadastrado!");
                                                }else{
                                                    System.out.println("Especie não cadastrada!");
                                                }
                                                break;
                                            case 2:
                                                System.out.print("Digite o id do alerta: ");
                                                alerta = alertaDao.selectId(teclado.nextInt());
                                                if(alerta!=null){
                                                    if(usuario.getEmail().equals(alerta.getEmailUsuario()) || usuario.getFuncao() == 3){
                                                        System.out.print("Deseja alterar a descrição(sim ou não): ");
                                                        aux = teclado.next();
                                                        if(aux.equalsIgnoreCase("sim")){
                                                            teclado.nextLine();
                                                            System.out.print("\nDigite a nova descrição: ");
                                                            alerta.setDescricao(teclado.nextLine());
                                                        }
                                                        System.out.print("Deseja a especie(sim ou não): ");
                                                        aux = teclado.next();
                                                        if(aux.equalsIgnoreCase("sim")){
                                                            System.out.print("\nDigite a nova especie: ");
                                                            alerta.setIdEspecie(teclado.nextInt());
                                                        }
                                                        System.out.println("Alerta atualizado!");
                                                        alertaDao.update(alerta.getId(),alerta);
                                                    }else{
                                                        System.out.println("Acesso Negado!");
                                                    }
                                                }else{
                                                    System.out.println("Alerta não cadastrado!");
                                                }
                                                break;
                                            case 3:
                                                System.out.print("Digite o id do alerta: ");
                                                alerta = alertaDao.selectId(teclado.nextInt());
                                                if(alerta!=null){
                                                    if(usuario.getEmail().equals(alerta.getEmailUsuario()) || usuario.getFuncao() == 3) {
                                                        mDao.setDeleteIdAlerta(alerta.getId());
                                                        alertaDao.delete(alerta.getId());
                                                        System.out.println("Alerta deletado!");
                                                    }else{
                                                        System.out.println("Acesso Negado!");
                                                    }
                                                }else{
                                                    System.out.println("Alerta não cadastrado!");
                                                }
                                                break;
                                            case 4:
                                                ArrayList<Alerta> list = alertaDao.selectAll();
                                                if(list.size() > 0){
                                                    for (Alerta aler: list
                                                         ) {
                                                        System.out.println("\n-------------------------------------");
                                                        System.out.println("ID: " + aler.getId());
                                                        System.out.println("Data: " + aler.getData());
                                                        System.out.println("Email do Usuario: " + aler.getEmailUsuario());
                                                        System.out.println("Especie: " + especieDao.selectId(aler.getIdEspecie()).getNome());
                                                        System.out.println("Descrição: " + aler.getDescricao());
                                                        System.out.println("\n-------------------------------------\n");
                                                    }
                                                }else{
                                                    System.out.println("Nenhum alerta foi cadastrado!");
                                                }
                                                break;
                                            case 0:
                                                System.out.println("Logou realizado com sucesso!");
                                                break;
                                        }
                                        break;
                                    case 2:
                                        especie = new Especie();
                                        System.out.print("1-Adicionar\n2-Editar\n3-Remover\n4-Listar\n0-Sair: ");
                                        op = teclado.nextInt();
                                        teclado.nextLine();
                                        switch (op){
                                            case 1:
                                                System.out.print("Digite a o nome da Especie: ");
                                                especie.setNome(teclado.nextLine().toUpperCase());
                                                if(especieDao.selectName(especie.getNome()) == null){
                                                    System.out.print("Digite como capturar a Especie: ");
                                                    especie.setComoCapturar(teclado.nextLine());
                                                    System.out.print("Digite como criar a Especie: ");
                                                    especie.setComoCriar(teclado.nextLine());
                                                    System.out.print("Digite sobre da Especie: ");
                                                    especie.setSobre(teclado.nextLine());
                                                    especie.setEmailUser(usuario.getEmail());
                                                    try{
                                                        especieDao.insert(especie);
                                                        System.out.println("Especie cadastrada com sucesso!");
                                                    }catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                }else{
                                                    System.out.println("Especia ja cadastrada no sistema!");
                                                }
                                                break;
                                            case 2:
                                                System.out.print("Digite o id da Especie: ");
                                                especie = especieDao.selectId(teclado.nextInt());
                                                if(especie!=null){
                                                    if(usuario.getEmail().equals(especie.getEmailUser()) || usuario.getFuncao() == 3){
                                                        System.out.print("Deseja alterar a Como capturar(sim ou não): ");
                                                        aux = teclado.next();
                                                        System.out.print("Digite a o nome da Especie: ");
                                                        especie.setNome(teclado.nextLine().toUpperCase());
                                                        System.out.println("Alerta atualizado!");
                                                        System.out.print("Digite como capturar a Especie: ");
                                                        especie.setComoCapturar(teclado.nextLine());
                                                        System.out.print("Digite como criar a Especie: ");
                                                        especie.setComoCriar(teclado.nextLine());
                                                        System.out.print("Digite sobre da Especie: ");
                                                        especie.setSobre(teclado.nextLine());
                                                        especie.setEmailUser(usuario.getEmail());
                                                        especieDao.update(especie.getId(),especie);
                                                    }else{
                                                        System.out.println("Acesso Negado!");
                                                    }
                                                }else{
                                                    System.out.println("Especie não cadastrada!");
                                                }
                                                break;
                                            case 3:
                                                System.out.print("Digite o id da Especie: ");
                                                especie = especieDao.selectId(teclado.nextInt());
                                                if(especie!=null){
                                                    if(usuario.getEmail().equals(especie.getEmailUser()) || usuario.getFuncao() == 3) {
                                                        especieDao.delete(especie.getId());
                                                        System.out.println("Especie deletada!");
                                                    }else{
                                                        System.out.println("Acesso Negado!");
                                                    }
                                                }else{
                                                    System.out.println("Especie não cadastrada!");
                                                }
                                                break;
                                            case 4:
                                                ArrayList<Especie> list = especieDao.selectAll();
                                                if(list.size() > 0){
                                                    for (Especie e: list
                                                    ) {
                                                        System.out.println("\n-------------------------------------");
                                                        System.out.println("ID: "+ e.getId());
                                                        System.out.println("Email do Usuario: " + e.getEmailUser());
                                                        System.out.println("Especie: " + e.getNome());
                                                        System.out.println("Descrição: " + e.getSobre());
                                                        System.out.println("\n-------------------------------------\n");
                                                    }
                                                }else{
                                                    System.out.println("Nenhum Especia foi cadastrada!");
                                                }
                                                break;
                                            }
                                            case 3:
                                                usuario = new Usuario();
                                                System.out.print("1-Editar\n2-Remover\n3-Listar\n0-Sair: ");
                                                op = teclado.nextInt();
                                                switch (op){
                                                    case 1:
                                                        teclado.nextLine();
                                                        System.out.print("Digite o email do Usuario: ");
                                                        usuario = usuarioDao.selectEmail(teclado.nextLine());
                                                        if(usuario!=null){
                                                            if(usuario.getFuncao() == 3){
                                                                System.out.println("Digite a nova função(1,2,3): ");
                                                                usuario.setFuncao(teclado.nextInt());
                                                                usuarioDao.update(usuario.getEmail(), usuario);
                                                            }else{
                                                                System.out.println("Acesso Negado!");
                                                            }
                                                        }else{
                                                            System.out.println("Usuario não cadastrado!");
                                                        }
                                                        break;
                                                    case 2:
                                                        teclado.next();
                                                        System.out.print("Digite o email do Usuario: ");
                                                        usuario = usuarioDao.selectEmail(teclado.nextLine());
                                                        if(usuario!=null){
                                                            if(usuario.getFuncao() == 3) {
                                                                usuarioDao.delete(usuario.getEmail());
                                                                System.out.println("Usuario deletado!");
                                                            }else{
                                                                System.out.println("Acesso Negado!");
                                                            }
                                                        }else{
                                                            System.out.println("Usuario não cadastrado!");
                                                        }
                                                        break;
                                                    case 3:
                                                        ArrayList<Usuario> list = usuarioDao.selectAll();
                                                        if(list.size() > 0){
                                                            for (Usuario u: list
                                                            ) {
                                                                System.out.println("\n-------------------------------------");
                                                                System.out.println("Email do Usuario: " + u.getEmail());
                                                                System.out.println("Especie: " + u.getNome());
                                                                System.out.println("Municipio: " + u.getIdMunicipio());
                                                                System.out.println("Função: " + u.getFuncao());
                                                                System.out.println("\n-------------------------------------\n");
                                                            }
                                                        }else{
                                                            System.out.println("Nenhum Especia foi cadastrado!");
                                                        }
                                                break;
                                            case 00:
                                                System.out.println("Logou realizado com sucesso!");
                                                break;
                                            }
                                        break;
                                    case 0:
                                        System.out.println("Logout realizado com sucesso!");
                                        break;
                                }
                            }while(op != 0);

                        }else{
                            System.out.println("Email ou senha invalido.Tente novamente!\n");
                        }
                    }else{
                        System.out.println("Email ou senha invalido.Tente novamente!\n");
                    }
                    break;
                case 2:
                    Municipio municipio = new Municipio();
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
                        municipio.setNome(teclado.nextLine());
                        municipio.setUf("RN");
                        if(municipioDao.selectNameAndUf(municipio.getNome(),municipio.getUf()) == null){
                            System.out.println("\nMunicipio Invalido!");
                            flag = false;
                        }
                    }while(!flag);
                    municipio = municipioDao.selectNameAndUf(municipio.getNome(),municipio.getUf());
                    usuario.setIdMunicipio(municipio.getId());
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