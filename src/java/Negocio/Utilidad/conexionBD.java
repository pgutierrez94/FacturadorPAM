 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio.Utilidad;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author desar
 */
public class conexionBD {
    
    private Connection conn = null;
    //private static final String dbURL = "jdbc:sqlserver://DESKTOP-R9PC2V8:1433;DatabaseName=DirOES;integratedSecurity=true;";OES:SQLDirectori0!
    private static final String dbURL = "jdbc:sqlserver://DESKTOP-R9PC2V8:1433;DatabaseName=DirOES;username=OES;password=pass1234;";
    
    public Connection getConnection() throws ClassNotFoundException {
        try {
            conn = DriverManager.getConnection(dbURL);
            System.out.println("Conexion exitosa");
        } catch (SQLException ex) {
            System.out.println("Error de BD: " + ex.toString());
        }
        return conn;
    }

    public void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) throws SQLException {
        if (con != null) {
            con.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (rs != null) {
            rs.close();
        }
    }
    public void closeConnection(Connection con, CallableStatement clstmt, ResultSet rs) throws SQLException {
        if (con != null) {
            con.close();
        }
        if (clstmt != null) {
            clstmt.close();
        }
        if (rs != null) {
            rs.close();
        }
    }
}
