<?php
require 'TeConexion.php';

if ($conexion) {
    if ($_SERVER["REQUEST_METHOD"] == "PUT") {
        $data = json_decode(file_get_contents("php://input"));

        $nomreunion = $data->nomreunion;
        $descripcion = $data->descripcion;
        $localizacion = $data->localizacion;
        $cupo = $data->cupo;
        $fecha = $data->fecha;
        $hora = $data->hora;

        $sql = "UPDATE eventos SET descripcion = ?, localizacion = ?, cupo = ?, fecha = ?, hora = ? WHERE nomreunion = ?";
        $stmt = mysqli_prepare($conexion, $sql);
        mysqli_stmt_bind_param($stmt, "ssisss", $descripcion, $localizacion, $cupo, $fecha, $hora, $nomreunion);
        $result = mysqli_stmt_execute($stmt);

        if ($result) {
            echo json_encode(array("message" => "Evento actualizado con éxito"));
        } else {
            echo json_encode(array("message" => "Error al actualizar evento: " . mysqli_error($conexion)));
        }
        mysqli_stmt_close($stmt);
    }
} else {
    echo "Error en la conexión: " . mysqli_connect_error();
}
?>
