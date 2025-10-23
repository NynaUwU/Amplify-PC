package view;

import controller.TrataClienteController;
import factory.Conector;
import java.net.*;

/**
 *
 * @author posse
 */
public class MusicServerAmplify {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            // Testando a conexão com o banco de dados
            if (Conector.getConnection() != null){
                System.out.println("Conectado com sucesso no banco de dados!");
            }
            
            //Iremos declarar o servidor socket
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Servidor inicializado! Aguardando conexões...");
            int idUnico = 0; // codigo para cada cliente
            while(true){ // looping infinito
                //a linha abaixo recebe a conexão do cliente
                Socket cliente = servidor.accept();
                System.out.println("Um novo cliente se conectou: "+cliente);
                idUnico++;
                // instanciar uma thread para que o cliente possa executar em separado 
                // de todos os outros clientes
                TrataClienteController tratacliente = new TrataClienteController(cliente, idUnico);
                tratacliente.start();// inicia a thread.  o start() executa o método RUN
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
