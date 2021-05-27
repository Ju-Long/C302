<?php 
include './dbFunctions.php';
header('Content-Type: application/json')

$query = "SELECT c.name , COUNT(f.category_id) AS 'number_films' FROM category c INNER JOIN film_category f ON c.category_id = f.category_id GROUP BY f.category_id";
$result = mysqli($link, $query);

echo json_encode($result);