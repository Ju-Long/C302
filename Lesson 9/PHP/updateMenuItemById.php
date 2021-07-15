<?php
include './dbFunctions.php';
include './checkAPIKey.php';
$menu_id = $_POST['menu_id'];
$category_id = $_POST['category_id'];
$description = $_POST['description'];
$price = $_POST['price'];

$query = "UPDATE menu_item SET menu_item_category_id = $category_id, menu_item_description = '$description', menu_item_unit_price = $price WHERE menu_item_id = $menu_id";
$result = mysqli_query($link, $query) or die(mysqli_error($link));

if ($result) {
        echo json_encode(['output' => 'success']);
} else {
        echo json_encode(['output' => 'failure']);
}
exit();

?>