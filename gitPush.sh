#!/bin/bash
git pull
git add *
git commit -m "$1"
git push
./INF2212_ProjetTutore/CONDUCT/gitPush.sh "$1"
