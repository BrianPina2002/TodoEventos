<?php
require 'TeConexion.php';

if ($conexion) {
    if ($_SERVER["REQUEST_METHOD"] == "GET") {
        $uid = $_GET['uid'] ?? '';

        $sql = "SELECT * FROM usuarios WHERE uid = ?";
        $stmt = mysqli_prepare($conexion, $sql);
        mysqli_stmt_bind_param($stmt, "s", $uid);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);

        $row = mysqli_fetch_assoc($result);
        if ($row) {
            header('Content-Type: application/json');
            echo json_encode(array("response" => 200, "content" => $row));
        } else {
            http_response_code(404);
            echo json_encode(array("response" => 404, "error" => "Perfil no encontrado"));
        }
        mysqli_stmt_close($stmt);
    }
} else {
    http_response_code(500);
    echo json_encode(array("response" => 500, "error" => "Error en la conexión: " . mysqli_connect_error()));
}
?>
