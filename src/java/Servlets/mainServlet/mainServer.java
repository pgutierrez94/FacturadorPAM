/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.mainServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Calendar;
import java.util.UUID;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import Estructuras.GestionUsuarios.usuario;
import Negocio.GestionUsuarios.gestionUsuarios;

/**
 *
 * @author desar
 */
@WebServlet(name = "mainServer", urlPatterns = {"/mainServer"})
public class mainServer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(mainServer.class);
    public usuario user = null;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            java.util.logging.Logger.getLogger(mainServer.class.getName()).log(Level.SEVERE, null, ex);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("ERROR!!!");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            java.util.logging.Logger.getLogger(mainServer.class.getName()).log(Level.SEVERE, null, ex);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("ERROR!!!");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet principal de comunicaciones FACEPAM.";
    }// </editor-fold>

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.json.JSONException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String metodo = "";
        String str = null;
        String respuesta = "";
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        JSONObject jObj = new JSONObject(sb.toString());
        metodo = jObj.getString("method");
        switch (metodo) {
            case "login":
                user = new usuario();
                gestionUsuarios gLogin = new gestionUsuarios();
                String token = null;
                this.user.setNickname(jObj.getString("username"));
                this.user.setPass(jObj.getString("password"));
                this.user.setActivo("false");
                //Validamos LogIn
                this.user = gLogin.validarCredenciales(this.user);
                if (this.user.getActivo().equals("true")) {
                    //Validamos fecha de expiracion
                    boolean valExpPass = false;
                    try {
                        if (this.user.getExpiracion() != null) {
                            Date today = new Date(Calendar.getInstance().getTime().getTime());
                            System.out.println(today);
                            System.out.println(this.user.getExpiracion());
                            //if (this.user.getExpiracion().compareTo(today) < 0) {
                            if (today.compareTo(this.user.getExpiracion()) < 0) {
                                valExpPass = false;
                            } else {
                                valExpPass = true;
                            }
                        } else {
                            valExpPass = true;
                        }
                    } catch (ClassCastException cc) {
                        cc.getMessage();
                    }
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", this.user.getNickname());
                    token = UUID.randomUUID().toString();
                    this.user.setToken(token);
                    session.setAttribute("token", token);
                    respuesta = "{user:" + this.user.getNickname()
                            + ",logged:" + String.valueOf(this.user.isLoggedIn())
                            + ",rol:" + this.user.getRol()
                            + ",activo:" + this.user.getActivo()
                            + ",valExpPass:" + String.valueOf(valExpPass)
                            + ",result:" + "ok"
                            + ",token:" + token + "}";
                } else {
                    respuesta = "{result:" + "ERROR"
                            + ",error: 'Usuario no Existe'}";
                }
                System.out.println(respuesta);
                JSONObject jResp = new JSONObject(respuesta);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jResp.toString());
                break;

            case "recuperaPass":
                String userP = jObj.getString("username");
                String correoP = jObj.getString("correo");
                gestionUsuarios gestionPass = new gestionUsuarios();
                boolean result = gestionPass.forgotPass(userP, correoP);
                if (result) {
                    respuesta = "{result:" + "ok}";
                } else {
                    respuesta = "{result:" + "ERROR}";
                }
                System.out.println(respuesta);
                JSONObject jResp1 = new JSONObject(respuesta);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jResp1.toString());
                break;

            default:
                break;
        }
    }
}
