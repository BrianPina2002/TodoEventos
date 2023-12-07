<?php
require 'TeConexion.php';

if ($conexion) {
    if ($_SERVER["REQUEST_METHOD"] == "GET") {
        $tipo = isset($_GET['tipo']) ? $_GET['tipo'] : '';

        if ($tipo != '') {
            $sql = "SELECT e.*, u.nombre as nombreAnfi
                    FROM eventos e
                    JOIN usuarios u ON e.uid = u.uid
                    WHERE e.tipo = '$tipo'";
        } else {
            $sql = "SELECT e.*, u.nombre as nombreAnfi
                    FROM eventos e
                    JOIN usuarios u ON e.uid = u.uid";
        }
        
        $result = mysqli_query($conexion, $sql);

        if ($result) {
            $response = array();
            while ($row = mysqli_fetch_assoc($result)) {
                $response[] = $row;
            }
            header('Content-Type: application/json');
            echo json_encode(array("response" => 200, "content" => $response));
        } else {
            http_response_code(500);
            echo json_encode(array("response" => 500, "error" => "Error en la consulta: " . mysqli_error($conexion)));
        }
    }
}
?>
