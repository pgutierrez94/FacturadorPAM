/* 
 Script index.js 
 Control de index para redireccion a login o a dashboard
 dev: fbetancourt
 date: 09/01/2019 
 */

console.log("Cargando script index...");

//Validacion de seguridad
$(document).ready(function () {
    console.log("ready!!");
    if (localStorage.getItem("Usuario") !== null && sessionStorage.getItem("TokenFACEPAM") !== null) {
        if (localStorage.getItem("Usuario").length <= 4 && sessionStorage.getItem("TokenFACEPAM").length < 36) {
            window.location.href = 'login.html';
            console.log("Go to login...");
        } else {
            window.location.href = 'Dashboard.html';
            console.log("Go to Dash...");
        }
    }else{
        window.location.href = 'login.html';
        console.log("Go to login...");
    }
});