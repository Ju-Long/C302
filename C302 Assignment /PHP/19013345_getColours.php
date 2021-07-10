<?php 
include './api_check.php';

$query_colour = "select * from colour";
$result_colour = mysqli_query($connection, $query_colour) or die (mysqli_error($connection));
$return_array = array();

foreach($result_colour as $i) {
    $return_array[] = $i;
}

if (empty($return_array)) {
    $return = ['output' => 'There is no colour available'];
    echo json_encode($return);
    exit();
}

$return = ['output' => 'success', 'result' => $return_array];
echo json_encode($return);
exit();