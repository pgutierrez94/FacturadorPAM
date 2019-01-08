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

function validaCredenciales() {
    event.preventDefault(); 
    clear();
    $(".btn").attr("disabled", true);
    $(".spinner").removeClass("displayNone");
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
                if (request.result === 'ok') {
                    console.log('Login...');
                    localStorage.setItem("Token",request.token);
                    localStorage.setItem("Usuario",request.user);
                } else {
                    console.log("Error de LogIn...");
                    localStorage.clear();
                    $(".alert-warning").removeClass("displayNone");
                    $(".alert-warning").addClass("displayBlock");
                }
            },
            error: function (errorThrown) {
                localStorage.clear();
                console.log(errorThrown);
                $(".alert-danger").removeClass("displayNone");
                $(".alert-danger").addClass("displayBlock");
            }
        });
    } else {
        localStorage.clear();
        console.log("Ingrese Credenciales");
        $(".alert-info").removeClass("displayNone");
        $(".alert-info").addClass("displayBlock");
    }     
    $(".btn").attr("disabled", false);
    $(".spinner").removeClass("displayBlock");
    $(".spinner").addClass("displayNone");
}

function cierraMensaje(clase){
    //console.log("cerrando mensaje...");
    $("."+clase).removeClass("displayBlock");
    $("."+clase).addClass("displayNone");
}

function clear(){    
    $(".alert-info").removeClass("displayBlock");
    $(".alert-info").addClass("displayNone");
    $(".alert-danger").removeClass("displayBlock");
    $(".alert-danger").addClass("displayNone");
    $(".alert-warning").removeClass("displayBlock");
    $(".alert-warning").addClass("displayNone");
}