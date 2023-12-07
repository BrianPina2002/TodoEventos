<?php
require 'TeConexion.php';

if ($conexion) {
    if ($_SERVER["REQUEST_METHOD"] == "PUT") {
        $data = json_decode(file_get_contents("php://input"));

        $nomreunion = $data->nomreunion;
        $uid = $data->uid;

        $sql = "INSERT INTO asistencia (nomreunion, uids) VALUES (?, ?)";
        $stmtInsert = mysqli_prepare($conexion, $sql);
        mysqli_stmt_bind_param($stmtInsert, "ss", $nomreunion, $uid);
        $result = mysqli_stmt_execute($stmtInsert);

        if ($result) {
            echo json_encode(array("message" => "Asistencia registrada"));
        } else {
            echo json_encode(array("message" => "Error: " . mysqli_error($conexion)));
        }

        mysqli_stmt_close($stmtInsert);
    }
} else {
    echo "Error en la conexión: " . mysqli_connect_error();
}
?>

