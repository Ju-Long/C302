<?php
include './dbFunctions.php';
include './checkAPIKey.php';
$menu_id = $_POST['menu_id'];

$query = "DELETE FROM menu_item WHERE menu_item_id = $menu_id";
$result = mysqli_query($link, $query) or die(mysqli_error($link));

if ($result) {
        echo json_encode(['output' => 'success']);
} else {
        echo json_encode(['output' => 'failure']);
}
exit();

?>