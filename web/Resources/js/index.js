/* 
 Script index.js 
 Control de index para redireccion a login o a dashboard
 dev: fbetancourt
 date: 09/01/2019 
 */

console.log("Cargando script index...");

$(document).ready(function(){
    console.log("ready!!");
    if(localStorage.getItem("Usuario").length<=4){
        window.location.href = 'login.html';
    }else{
        console.log("Go to Dash...");
    }
});