<?php
include './DBConnection.php';
header('Content-Type: application/json');

$username = $_POST['username'] ?? '';
$password = $_POST['password'] ?? '';

if (!($username && $password)){
    echo json_encode(['output' => 'Invalid Input']);
    exit();
}

$return = array();
$return_array = array();
$password = sha1($password);
$query = "select * from user where username = '$username' and password = '$password'";
$result = mysqli_query($connection, $query) or die(mysqli_error($connection));

foreach($result as $i) {
    $return_array = $i;
}

if (empty($return_array)) {
    $return = ['output' => 'username or password wrong'];
} else {
    $return = ['output' => 'success', 'result' => $return_array];
}

echo json_encode($return);
exit();