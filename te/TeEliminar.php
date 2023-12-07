<?php
require 'TeConexion.php';

if ($conexion) {
    if ($_SERVER["REQUEST_METHOD"] == "PUT") {
        parse_str(file_get_contents("php://input"), $_PUT);
        $nomreunion = $_PUT['nomreunion'] ?? '';

        $sql = "DELETE FROM eventos WHERE nomreunion = ?";
        $stmt = mysqli_prepare($conexion, $sql);
        mysqli_stmt_bind_param($stmt, "s", $nomreunion);
        $result = mysqli_stmt_execute($stmt);

        if ($result) {
            echo json_encode(array("message" => "Evento eliminado con éxito", "affected_rows" => mysqli_stmt_affected_rows($stmt)));
        } else {
            echo json_encode(array("message" => "Error al eliminar evento: " . mysqli_error($conexion), "sql_error" => mysqli_stmt_error($stmt)));
        }        
        mysqli_stmt_close($stmt);
    }
} else {
    echo "Error en la conexión: " . mysqli_connect_error();
}
?>
