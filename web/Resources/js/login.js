/* 
 Script Login.js 
 Control de login a FACEPAM
 dev: fbetancourt
 date: 04/01/2019
 
 Estándar para los llamados HTTP
 
 $.ajax({
 url: 'http://localhost:8080/FacturadorPAM/mainServer',
 type: 'post',
 dataType: 'json',
 contentType: 'application/json',
 data: datos,
 success: function (request) {
 console.log(request);
 },
 error: function (errorThrown) {
 console.log(errorThrown);
 }
 });
 
 */

console.log("Cargando script login...");

//Global vars
var flagTry = 0;

//Validacion de seguridad
$(document).ready(function () {
    console.log("ready!!");
    if (localStorage.getItem("Usuario") !== null && sessionStorage.getItem("TokenFACEPAM") !== null) {
        if (localStorage.getItem("Usuario").length <= 4 && sessionStorage.getItem("TokenFACEPAM").length < 36) {
            window.location.href = 'login.html';
        } else {
            window.location.href = 'Dashboard.html';
            console.log("Go to Dash...");
        }
    }
});

function validaCredenciales() {
    console.log(flagTry);
    $(".btn").attr("disabled", true);
    event.preventDefault();
    clear();
    $(".spinner").addClass("displayBlock");
    var nickname = $("#username").val();
    var password = $("#password").val();
    var method = "login";
    var datos = '{username: "' + nickname + '", password : "' + password + '", method : "' + method + '"}';
    if (nickname.length > 1 && password.length > 1) {
        $.ajax({
            url: 'http://localhost:8080/FacturadorPAM/mainServer',
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: datos,
            success: function (request) {
                console.log(request);
                if (flagTry < 3) {
                    if (request.result === 'ok' && request.activo === true) {
                        if (request.logged === true) {
                            console.log('Login...');
                            sessionStorage.setItem("TokenFACEPAM", request.token);
                            localStorage.setItem("Usuario", request.user);
                            if (request.valExpPass === true) {
                                console.log("Cambio de contraseña");
                            } else {
                                //window.location.href = 'Dashboard';
                                console.log("Go to Dash...");
                            }
                        }
                    } else {
                        flagTry++;
                        localStorage.removeItem("Usuario");
                        sessionStorage.removeItem("TokenFACEPAM");
                        $(".alert-warning").removeClass("displayNone");
                        $(".alert-warning").addClass("displayBlock");
                        console.log("Error de LogIn...");
                    }
                } else {
                    console.log("Intentos Excedidos...");
                    clear();
                    sessionStorage.setItem("TokenFACEPAM", null);
                    localStorage.setItem("Usuario", "Bloqueado");
                    $(".alert-danger").removeClass("displayNone");
                    $(".alert-danger").addClass("displayBlock");
                    deleteLS();
                }
            },
            error: function (errorThrown) {
                localStorage.clear();
                sessionStorage.clear();
                console.log(errorThrown);
                $(".alert-danger").removeClass("displayNone");
                $(".alert-danger").addClass("displayBlock");
            }
        });
    } else {
        console.log("Ingrese Credenciales");
        $(".alert-info").removeClass("displayNone");
        $(".alert-info").addClass("displayBlock");
    }
    $(".btn").attr("disabled", false);
    $(".spinner").removeClass("displayBlock");
}

function cierraMensaje(clase) {
    //console.log("cerrando mensaje...");
    $("." + clase).removeClass("displayBlock");
    $("." + clase).addClass("displayNone");
}

function clear() {
    $(".alert-info").removeClass("displayBlock");
    $(".alert-info").addClass("displayNone");
    $(".alert-danger").removeClass("displayBlock");
    $(".alert-danger").addClass("displayNone");
    $(".alert-warning").removeClass("displayBlock");
    $(".alert-warning").addClass("displayNone");
    $(".alert-success").removeClass("displayBlock");
    $(".alert-success").addClass("displayNone");
}

function deleteLS() {
    $(".btn").attr("disabled", true);
    $("#username").val('');
    $("#password").val('');
    setTimeout(function () {
        //console.log("Limpiando LS...");
        sessionStorage.removeItem("TokenFACEPAM");
        localStorage.removeItem("Usuario");
        flagTry = 0;
        $(".btn").attr("disabled", false);
        clear();
    }, 30000);
}

function recuperaPass() {
    //console.log("Recupera Password...");
    $(".btn").attr("disabled", true);
    event.preventDefault();
    clear();
    var nickname = $("#usernameP").val();
    var correo = $("#correoP").val();
    var method = "recuperaPass";
    var datos = '{username: "' + nickname + '", correo : "' + correo + '", method : "' + method + '"}';
    if (nickname.length > 1 && correo.length > 1) {
        $.ajax({
            url: 'http://localhost:8080/FacturadorPAM/mainServer',
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: datos,
            success: function (request) {
                console.log(request);
                if (request.result === "ok") {
                    $(".alert-success").removeClass("displayNone");
                    $(".alert-success").addClass("displayBlock");
                } else {
                    $(".alert-warning").removeClass("displayNone");
                    $(".alert-warning").addClass("displayBlock");
                }
            },
            error: function (errorThrown) {
                console.log(errorThrown);
                $(".alert-danger").removeClass("displayNone");
                $(".alert-danger").addClass("displayBlock"); 
            }
        });
    } else {
        console.log("Ingrese Datos.");
        $(".alert-info").removeClass("displayNone");
        $(".alert-info").addClass("displayBlock");
    }
    $(".btn").attr("disabled", false);
}