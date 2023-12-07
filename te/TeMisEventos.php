<?php
require 'TeConexion.php';

if ($conexion) {
    if ($_SERVER["REQUEST_METHOD"] == "GET") {
        $uid = $_GET['uid'] ?? '';  

        $sql = "SELECT e.*, u.nombre as nombreAnfi FROM eventos e 
                JOIN usuarios u ON e.uid = u.uid WHERE e.uid = ?";
        $stmt = mysqli_prepare($conexion, $sql);
        mysqli_stmt_bind_param($stmt, "s", $uid);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);

        $response = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $response[] = $row;
        }
        header('Content-Type: application/json');
        echo json_encode(array("response" => 200, "content" => $response));
        mysqli_stmt_close($stmt);
    }
} else {
    http_response_code(500);
    echo json_encode(array("response" => 500, "error" => "Error en la conexión: " . mysqli_connect_error()));
}
?>

