gcc initialisation.c -o init
gcc creatMdj.c -o creatMdj
gcc readMdj.c -o readMdj
gcc clean.c -o clean

echo "Lancement d'un rédacteur puis 2 lecteurs puis 1 rédacteur puis un lecteur"

./init
./creatMdj
./readMdj &
./readMdj &
./creatMdj
./readMdj
./clean
