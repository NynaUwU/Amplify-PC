/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.example.musicplayer.Musica;
import com.example.musicplayer.database.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
/**
 *
 * @author herrmann
 */
public class ConexaoController {

    // esta classe enviará e receberá coisas para o servidor
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private OutputStream outMP3;
    private InputStream inMP3;
    private Usuario userLogado;
    private Socket cliente;

    public Usuario getUserLogado() {
        return userLogado;
    }

    public void setUserLogado(Usuario userLogado) {
        this.userLogado = userLogado;
    }

    public ConexaoController(ObjectOutputStream out, ObjectInputStream in) {
        this.out = out;
        this.in = in;
    }

    public Usuario usuarioLogin(Usuario user) {
        try {
            // enviando comando "UsuarioLogin" para o servidor
            out.writeObject("UsuarioLogin");
            // lendo ok 
            in.readObject();
            // enviando o email e senha
            out.writeObject(user);
            // recebendo usuário ou nulo do servidor
            Usuario userLogado = (Usuario) in.readObject();
            return userLogado;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void transfer(ObjectOutputStream out, ObjectInputStream in, InputStream inMP3, OutputStream outMP3) {
        this.out = out;
        this.in = in;
        this.inMP3 = inMP3;
        this.outMP3 = outMP3;
    }


    public boolean cadastrarMusica(Musica music) throws IOException, ClassNotFoundException {
        if (userLogado != null) {
            music.setUsuarioId(userLogado.getCodUsuario());
            try {
                out.writeObject("cadastrarMusica");
                in.readObject(); // lendo o "OK"
                out.writeObject(music);
                in.readObject(); // lendo o "OK"
                FileInputStream fileInputStream = new FileInputStream(music.getArquivo());
                File archiveFile = new File(music.getArquivo());
                out.writeObject(archiveFile.length());
                in.readObject(); // lendo o "OK"
                out.flush();
                long size = archiveFile.length();

                final int buffer_size = 4096;
                try {
                    byte[] bytes = new byte[buffer_size];
                    for (int count = 0, prog = 0; count != -1;) {
                        count = fileInputStream.read(bytes);
                        if (count % 10 == 0) {
                            System.out.println(String.valueOf(((long) prog) * 100 / size));
                        }
                        if (count != -1) {
                            outMP3.write(bytes, 0, count);
                            prog = prog + count;

                        }
                    }
                    outMP3.flush();
                    out.flush();
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                in.readObject();
                in.readObject();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean cadastrarUser(Usuario User) {
        boolean result = false;
        if (cliente.isConnected()) {
            try {
                this.out.writeObject("CadastroUser");
                this.in.readObject(); // lendo o "OK"
                this.out.writeObject(User);
                result = (boolean) in.readObject();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return result;
        }

    }

}
