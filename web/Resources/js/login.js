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
                            sessionStorage.setItem("token", request.token);
                            localStorage.setItem("Usuario", request.user);
                            if (request.valExpPass === 'true') {
                                console.log("Cambio de contraseña");
                            } else {
                                //window.location.href = 'Dashboard';
                                console.log("Go to Dash...");
                            }
                        }
                    } else {
                        flagTry++;
                        localStorage.removeItem("Usuario");
                        sessionStorage.removeItem("token");
                        $(".alert-warning").removeClass("displayNone");
                        $(".alert-warning").addClass("displayBlock");
                        console.log("Error de LogIn...");
                    }
                } else {
                    console.log("Intentos Excedidos...");
                    clear();
                    sessionStorage.setItem("token", null);
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
}

function deleteLS() {
    $(".btn").attr("disabled", true);
    $("#username").val('');
    $("#password").val('');
    setTimeout(function () {
        //console.log("Limpiando LS...");
        sessionStorage.removeItem("token");
        localStorage.removeItem("Usuario");
        flagTry = 0;
        $(".btn").attr("disabled", false);
        clear();
    }, 30000);
}