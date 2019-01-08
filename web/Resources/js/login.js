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
    var nickname = $("#username").val();
    var password = $("#password").val();
    const Http = new XMLHttpRequest();
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
            },
            error: function (errorThrown) {
                console.log(errorThrown);
            }
        });
    } else {
        console.log("Ingrese Credenciales");
    }
}
