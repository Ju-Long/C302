<?php 
include './dbFunctions.php';
header('Content-Type: application/json')

$id = $_GET['id'] ?? 0;

$query = "SELECT * FROM film F INNER JOIN film_category FC ON F.film_id = FC.film_id WHERE F.film_id=$id ORDER BY F.film_id;";
$result = mysqli($link, $query);

echo json_encode($result);