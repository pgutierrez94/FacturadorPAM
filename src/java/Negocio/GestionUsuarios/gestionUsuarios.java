/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio.GestionUsuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Estructuras.GestionUsuarios.usuario;
import Negocio.Utilidad.*;
import java.util.UUID;

/**
 *
 * @author desar
 */
public class gestionUsuarios {

    public usuario validarCredenciales(usuario user) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        conexionBD dataBase = null;
        queriesSQL queries = null;
        try {
            dataBase = new conexionBD();
            queries = new queriesSQL();
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = dataBase.getConnection();
            String sql;
            sql = queries.getConsulta("consultaLogin");
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getNickname());
            stmt.setString(2, cifrado.cifra(user.getPass()));
            System.out.println(cifrado.cifra(user.getPass()));
            rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("entra al RS...");
                    user.setNickname(rs.getString("nickname"));
                    user.setPass(rs.getString("password"));
                    user.setLoggedIn(true);
                    user.setExpiracion(rs.getDate("expiracion_password"));
                    user.setRol(rs.getInt("rol_aplicacion"));
                    user.setActivo(rs.getString("activo"));
                }
            } else {
                user.setNickname(null);
                user.setPass(null);
                user.setLoggedIn(false);
                user.setExpiracion(null);
                user.setRol(0);
                user.setActivo("false");
            }
            try {
                dataBase.closeConnection(con, stmt, rs);
            } catch (SQLException e) {
                Logger.getLogger(gestionUsuarios.class.getName()).log(Level.SEVERE, null, e);
            }
        } catch (ClassNotFoundException | SQLException ex1) {
            Logger.getLogger(gestionUsuarios.class.getName()).log(Level.SEVERE, null, ex1);
        }
        return user;
    }

    public boolean forgotPass(String user, String emailP) {
        boolean result = false;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        conexionBD dataBase = null;
        queriesSQL queries = null;
        correo mail = null;
        String correoV = "";
        try {
            dataBase = new conexionBD();
            queries = new queriesSQL();
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = dataBase.getConnection();
            String sql;
            sql = queries.getConsulta("validaRecuperaPass");
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user);
            rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    correoV = rs.getString("email");
                }
                //si existe, validamos que el correo coincida
                if (emailP.equals(correoV)) {
                    //luego, prodecemos a setear el password   
                    sql = queries.getConsulta("forgotPass");
                    String new_pass = UUID.randomUUID().toString();
                    stmt = con.prepareStatement(sql);
                    stmt.setString(1, cifrado.cifra(new_pass));
                    System.out.println(new_pass);
                    System.out.println(cifrado.cifra(new_pass));
                    stmt.setString(2, user);
                    stmt.execute();
                    result = true;
                    mail = new correo();
                    mail.SendMail(new_pass, correoV);
                }
            }
            try {
                dataBase.closeConnection(con, stmt, rs);
            } catch (SQLException e) {
                Logger
                        .getLogger(gestionUsuarios.class
                                .getName()).log(Level.SEVERE, null, e);
            }
        } catch (ClassNotFoundException | SQLException ex1) {
            Logger
                    .getLogger(gestionUsuarios.class
                            .getName()).log(Level.SEVERE, null, ex1);
        }
        return result;
    }

}
