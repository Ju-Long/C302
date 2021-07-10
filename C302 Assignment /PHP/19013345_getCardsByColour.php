<?php
include './api_check.php';

$query_card = "select * from card";
$return_array = array();

if (isset($_POST['colour_id'])) {
    $colour_id = $_POST['colour_id'];
    $query_card = "select * from card where colourId = $colour_id";
}
$result_card = mysqli_query($connection, $query_card) or die(mysqli_error($connection));

foreach($result_card as $i) {
    $return_array[] = $i;
}

if (empty($return_array)) {
    $return = ['output' => 'No card found'];
    echo json_encode($return);
    exit();
}

$return = ['output' => 'success', 'result' => $return_array];
echo json_encode($return);
exit();
