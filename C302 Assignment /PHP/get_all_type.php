<?php
include './api_check.php';

$return_array = array();
$query_type = "select * from type";
$result_type = mysqli_query($connection, $query_type) or die(mysqli_error($connection));

foreach($result_type as $i) {
    $return_array[] = $i;
}

if (empty($return_array)) {
    $return = ['output' => 'No data found in type'];
    echo json_encode($return);
    exit();
}

$return = ['output' => 'success', 'result' => $return_array];
echo json_encode($return);
exit();