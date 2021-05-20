<?php 
include './dbFunctions.php';
header('Content-Type: application/json')

$query = "SELECT category_id, name FROM category";
$result = mysqli($link, $query);

echo json_encode($result);