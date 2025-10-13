/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelDominio;

import java.io.Serializable;

/**
 *
 * @author herrmann 
 */
public class Administrador extends Usuario implements Serializable{
    private static final long serialVersionUID = 123456789L;

    // SELECTS e UPDATES
    public Administrador(int codUsuario, String nome, String email, String senha) {
        super(codUsuario, nome, email, senha);
    }
    
    //DELETES
    public Administrador(int codUsuario) {
        super(codUsuario);
    }

    //INSERTS
    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public String toString() {
        return super.toString() + "Administrador{" + '}';
    }
    
    
    
    
}
