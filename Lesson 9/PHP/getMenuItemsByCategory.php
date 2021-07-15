<?php
include './dbFunctions.php';
include './checkAPIKey.php';
$category_id = $_GET['category_id'];
$response = array();

$query = "SELECT * FROM menu_item WHERE menu_item_category_id = $category_id";
$result = mysqli_query($link, $query) or die(mysqli_error($link));

for($row = mysqli_fetch_assoc($result)) {
        $response[] = $row;
}

echo json_encode($response);

?>