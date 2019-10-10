#!/bin/bash

echo "Delai entre producteur et consommateur (en s)?"

read delay

./initialisation fic.txt
./producteur fic.txt &
sleep $delay
./consommateur fic.txt
./clean fic.txt
ipcs
