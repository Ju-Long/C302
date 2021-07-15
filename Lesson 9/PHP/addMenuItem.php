<?php
include './dbFunctions.php';
include './checkAPIKey.php';
$category_id = $_POST['category_id'];
$description = $_POST['description'];
$price = $_POST['price'];

$query = "INSERT INTO menu_item(menu_item_category_id, menu_item_description, menu_item_unit_price) VALUES ($category_id, '$description', $price)";
$result = mysqli_query($link, $query) or die(mysqli_error($link));

if ($result) {
        echo json_encode(['output' => 'success'];
} else {
        echo json_encode(['output' => 'failure'];
}

exit();
?>