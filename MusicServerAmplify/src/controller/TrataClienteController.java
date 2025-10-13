/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import model.UsuarioDao;
import modelDominio.Usuario;


/**
 *
 * @author herrmann
 */
// essa classe irá cuidar da comunicação com o cliente. 
// e como multiplos clientes se conectam, a única forma
//de tornar isso viável é utilizando THREADS
public class TrataClienteController extends Thread{
    private Socket cliente;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int idUnico;

    public TrataClienteController(Socket cliente, int idUnico) {
        this.cliente = cliente;
        this.idUnico = idUnico;
        try {
            // criando o objeto IN para o servidor poder receber os
            // comandos do cliente. 
            this.in = new ObjectInputStream(cliente.getInputStream());
            // o OUT é usado para enviar coisas para o cliente            
            this.out = new ObjectOutputStream(cliente.getOutputStream());
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String comando; 
        System.out.println("Esperando comandos do cliente "+idUnico);
        try {
            // esperando comando do cliente
            comando = (String) in.readObject();
            // enquanto o comando for diferente de "FIM" o servidor
            // fica dentro do looping
            while (! comando.equalsIgnoreCase("fim")){
                System.out.println("Cliente "+idUnico+" enviou o comando "+comando);
                /// TEREMOS ZILHARES DE IF's para testar o comando
                if (comando.equalsIgnoreCase("UsuarioLogin")){
                    // enviando OK para cliente
                    out.writeObject("ok");
                    // lendo o usuario que veio do Cliente
                    Usuario user = (Usuario) in.readObject();
                    System.out.println(user);
                    // Criando o UsuarioDao para chamar o método de login
                    UsuarioDao usDao = new UsuarioDao();
                    Usuario userLogado = usDao.login(user);
                    out.writeObject(userLogado);
                }else if (comando.equalsIgnoreCase("UsuarioLista")){
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
            System.out.println("Cliente "+idUnico+" fechou a conexão");
            in.close();
            out.close();
            cliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }        
}
