/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio.Utilidad;

/**
 *
 * @author desar
 */
public class queriesSQL {
   private final String consultaLogin = "select u.nickname,u.password,u.expiracion_password,u.rol_aplicacion,u.activo from Usuarios as u where nickname = ? and password = ?";
    public String result = "";
    
    public String getConsulta(String consulta) {
        if (consulta != null) {
            switch (consulta) {
                case ("consultaLogin"):
                    result = consultaLogin;
                    break;
            }
        }
        return result;
    }
}
