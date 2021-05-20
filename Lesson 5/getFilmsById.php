<?php 
include './dbFunctions.php';
header('Content-Type: application/json')

$id = $_GET['id'] ?? 0;
$actorName = [];
$film = array();

$query = "SELECT * from film F INNER JOIN film_actor FA ON F.film_id = FA.film_id INNER JOIN actor A ON FA.actor_id = A.actor_id WHERE F.film_id=$id";
$result = mysqli($link, $query);

foreach ($result as $i) {
    $film['film_id'] = $i->film_id;
    $film['title'] = $i->title;
    $film['description'] = $i->description;
    $film['release_year'] = $i->release_year;
    $film['rating'] = $i->rating;

    array_push($actorName, "$i->first_name $i->last_name");
}
$film['actors'] = $actorName;

echo json_encode($film);