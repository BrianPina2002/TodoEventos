<?php
require 'TeConexion.php';

if ($conexion){
    if ($_SERVER["REQUEST_METHOD"] == "PUT") {
        
        $data       = json_decode(file_get_contents("php://input"));
        $uid        = $data->uid;
        $nombre     = $data->nombre;
        $apellido   = $data->apellido;
        $correo     = $data->correo;
        $evento     = $data->evento;
        $estado     = $data->estado;
        $ciudad     = $data->ciudad;
    
        $sql = "INSERT INTO usuarios (uid, nombre, apellido, correo, evento, estado, ciudad) 
        VALUES (?, ?, ?, ?, ?, ?, ?)";
        $stmtInsert = mysqli_prepare($conexion, $sql);
        mysqli_stmt_bind_param($stmtInsert, "sssssss", $uid, $nombre, $apellido, $correo, $evento, $estado, $ciudad);

        if (mysqli_stmt_execute($stmtInsert)) {
            echo json_encode(array("message" => "Registro insertado correctamente"));
        } else {
            echo json_encode(array("message" => "Error en la inserción: " . mysqli_error($conexion)));
        }
        mysqli_stmt_close($stmtInsert);
    }
}else {
    echo "Error en la conexión: " . mysqli_connect_error();
}
?>
