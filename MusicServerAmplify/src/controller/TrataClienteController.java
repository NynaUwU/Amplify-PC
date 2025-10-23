package controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import model.MusicaDao;
import model.UsuarioDao;
import com.example.musicplayer.Musica;
import com.example.musicplayer.database.Usuario;
import java.io.File;

public class TrataClienteController extends Thread {

    private Socket cliente;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private OutputStream outMP3;
    private InputStream inMP3;
    private int idUnico;

    public TrataClienteController(Socket cliente, int idUnico) {
        this.cliente = cliente;
        this.idUnico = idUnico;
        try {
            // criando o objeto IN para o servidor poder receber os
            // comandos do cliente. 
            this.in = new ObjectInputStream(cliente.getInputStream());
            inMP3 = cliente.getInputStream();
            // o OUT é usado para enviar coisas para o cliente            
            this.out = new ObjectOutputStream(cliente.getOutputStream());
            outMP3 = cliente.getOutputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String comando;
        System.out.println("Esperando comandos do cliente " + idUnico);
        try {
            // esperando comando do cliente
            comando = (String) in.readObject();
            // enquanto o comando for diferente de "FIM" o servidor
            // fica dentro do looping
            while (!comando.equalsIgnoreCase("fim")) {
                System.out.println("Cliente " + idUnico + " enviou o comando " + comando);
                /// TEREMOS ZILHARES DE IF's para testar o comando
                if (comando.equalsIgnoreCase("UsuarioLogin")) {
                    // enviando OK para cliente
                    out.writeObject("ok");
                    // lendo o usuario que veio do Cliente
                    Usuario user = (Usuario) in.readObject();
                    System.out.println(user);
                    // Criando o UsuarioDao para chamar o método de login
                    UsuarioDao usDao = new UsuarioDao();
                    Usuario userLogado = usDao.login(user);
                    out.writeObject(userLogado);
                } else if (comando.equalsIgnoreCase("UsuarioLista")) {
                    UsuarioDao uDao = new UsuarioDao();
                    ArrayList<Usuario> lista = uDao.getLista();
                    out.writeObject(lista);
                } else if (comando.equalsIgnoreCase("cadastrarMusica")) {
                    out.writeObject("ok");

                    MusicaDao mDao = new MusicaDao();
                    Musica musica = (Musica) in.readObject();

                    Path path = Paths.get(musica.getArquivo());
                    String fileNameWithExtension = path.getFileName().toString();
                    musica.setArquivo(fileNameWithExtension);
                    FileOutputStream fileOutputStream = new FileOutputStream(fileNameWithExtension);

                    out.writeObject("ok");

                    long size = (long) in.readObject();
                    out.writeObject("ok");

                    byte[] buffer = new byte[4096]; // Or another appropriate buffer size

                    for (int count = 0, prog = 0; count != -1;) {
                        count = inMP3.read(buffer);
                        fileOutputStream.write(buffer, 0, count);
                        prog = prog + count;
                        System.out.println(((long) prog) * 100 / size);
                        File test = new File(fileNameWithExtension);
                        if (test.length()==size) {
                            count=-1;
                        }
                        System.out.println("arquivo no pc:" + test.length() + "///" + size);
                    }
                    fileOutputStream.close();
                    //fileOutputStream.write(buffer, 0, bytesRead);

                    System.out.println("Recebido com sucesso");
                    System.out.println(mDao.cadastrarMusica(musica));

                    out.writeObject("ok");
                    out.writeObject("ok");
                    out.flush();

                    // TODO arquivo
                } else if (comando.equalsIgnoreCase("getListaMusica")) {
                    UsuarioDao uDao = new UsuarioDao();
                    ArrayList<Usuario> lista = uDao.getLista();
                    out.writeObject(lista);
                } else if (comando.equalsIgnoreCase("getMusicasPorUsuario")) {
                    UsuarioDao uDao = new UsuarioDao();
                    ArrayList<Usuario> lista = uDao.getLista();
                    out.writeObject(lista);
                } else if (comando.equalsIgnoreCase("getMusicasPorGenero")) {
                    UsuarioDao uDao = new UsuarioDao();
                    ArrayList<Usuario> lista = uDao.getLista();
                    out.writeObject(lista);
                } else if (comando.equalsIgnoreCase("buscarPorArtista")) {
                    UsuarioDao uDao = new UsuarioDao();
                    ArrayList<Usuario> lista = uDao.getLista();
                    out.writeObject(lista);
                } else if (comando.equalsIgnoreCase("buscarPorNome")) {
                    UsuarioDao uDao = new UsuarioDao();
                    ArrayList<Usuario> lista = uDao.getLista();
                    out.writeObject(lista);
                } else if (comando.equalsIgnoreCase("buscarPorNome")) {
                    UsuarioDao uDao = new UsuarioDao();
                    ArrayList<Usuario> lista = uDao.getLista();
                    out.writeObject(lista);
                } else if (comando.equalsIgnoreCase("getGeneros")) {
                    UsuarioDao uDao = new UsuarioDao();
                    ArrayList<Usuario> lista = uDao.getLista();
                    out.writeObject(lista);
                } else if (comando.equalsIgnoreCase("contarMusicasPorGenero")) {
                    UsuarioDao uDao = new UsuarioDao();
                    ArrayList<Usuario> lista = uDao.getLista();
                    out.writeObject(lista);
                }

                // nunca apgar a linha abaixo. pois estaremos relendo o comando
                // se apagarmos nosso programa ficará trancado
                comando = (String) in.readObject();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // fechando as conexões com o cliente
            System.out.println("Cliente " + idUnico + " fechou a conexão");
            in.close();
            out.close();
            cliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
