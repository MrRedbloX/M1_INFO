param($commit)
git pull
git add *
git commit -m $commit
git push
.\INF2212_ProjetTutore\CONDUCT\gitPush $commit