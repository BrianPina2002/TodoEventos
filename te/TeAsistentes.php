<?php
require 'TeConexion.php';

if ($conexion) {
    if ($_SERVER["REQUEST_METHOD"] == "GET") {
        $nomreunion = $_GET['nomreunion'] ?? '';

        $sql = "SELECT u.nombre FROM asistencia a
                JOIN usuarios u ON a.uids = u.uid
                WHERE a.nomreunion = ?";
        $stmt = mysqli_prepare($conexion, $sql);
        mysqli_stmt_bind_param($stmt, "s", $nomreunion);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);

        $nombres = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $nombres[] = $row['nombre'];
        }
        header('Content-Type: application/json');
        echo json_encode(array("response" => 200, "nombres" => $nombres));
    }
} else {
    echo "Error en la conexión: " . mysqli_connect_error();
}
?>
