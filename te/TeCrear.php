<?php
require 'TeConexion.php';

if ($conexion){
    if ($_SERVER["REQUEST_METHOD"] == "PUT") {
        
        $data           = json_decode(file_get_contents("php://input"));
        $uid            = $data->uid;
        $nomreunion     = $data->nomreunion; 
        $descripcion    = $data->descripcion;
        $localizacion   = $data->localizacion;
        $tipo           = $data->tipo; 
        $ocupado        = $data->ocupado;
        $cupo           = $data->cupo;
        $fecha          = $data->fecha;
        $hora           = $data->hora;

        $fechaObj = DateTime::createFromFormat('Y-m-d', $fecha);
        if (!$fechaObj || $fechaObj->format('Y-m-d') !== $fecha) {
            echo json_encode(array("message" => "Formato de fecha inválido"));
            exit;
        }

        $sql = "INSERT INTO eventos (
        uid, nomreunion, descripcion, localizacion, tipo, ocupado, cupo, fecha, hora) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        $stmtInsert = mysqli_prepare($conexion, $sql);
        mysqli_stmt_bind_param($stmtInsert, "sssssiiis", $uid, $nomreunion, $descripcion, $localizacion, $tipo, $ocupado, $cupo, $fecha, $hora);
        $result = mysqli_stmt_execute($stmtInsert);

        if ($result) {
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