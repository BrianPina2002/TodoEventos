<?php
$conexion = mysqli_connect('localhost','root','','todoeventos');

if($conexion){
    mysqli_set_charset($conexion, 'UTF-8');
}

?>