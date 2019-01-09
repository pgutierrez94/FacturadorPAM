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
   private final String validaRecuperaPass = "select nickname, email from usuarios where nickname = ?";
   private final String forgotPass = "update Usuarios set password = ?, expiracion_password = DATEADD(month, -1, GETDATE()) where nickname = ?";
   public String result = "";
    
    public String getConsulta(String consulta) {
        if (consulta != null) {
            switch (consulta) {
                case ("consultaLogin"):
                    result = consultaLogin;
                    break;
                case ("validaRecuperaPass"):
                    result = validaRecuperaPass;
                    break;
                case ("forgotPass"):
                    result = forgotPass;
                    break;
            }
        }
        return result;
    }
}
