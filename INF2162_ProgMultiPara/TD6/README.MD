TD4 CavalierEuler par COGOLUEGNES Charles
Execution : scala CavalierEuler.jar

Temps d'exécution avant heuristique :
5x5 : 0.119s
6x6 : 0.493s
7x7 : 60.019s
8x8 : 4.338s
9x9 : 14.774s
10x10 : ?

Ces temps d'exécution ne font pas vraiment sens car ils dépendent de l'ordre dans laquelle sont données les coordonnées de déplacement.
Si pour un echiquier plus grand on met moins de temps, c'est de la "chance".

Pour preuve voici les temps d'exécution avec l'heuristique :
5x5 : 0.068s
6x6 : 0.081s
7x7 : 0.097s
8x8 : 0.303s
9x9 : 0.485s
10x10 : 3.390s
11x11 : 0.201s
12x12 : ?

On observe une augmentation plus linéaire (sauf pour le 11x11 où l'on voit que le facteur "chance" est toujours présent).
Néanmoins on pène à trouver une solution pour de plus grand échiquier.
Cela vient du fait que plus l'échiquier est grand, plus l'heuristique actuel devient inutile.
En effet la taille du carré composé de 8 au milieu augmente.

On va donc modifier l'heuristique dynamiquement afin qu'il prenne en compte les étapes précédentes.
On arrive donc aux résultats suivants :
10x10 : 0.138s
15x15 : 0.284s
20x20 : 0.416s
30x30 : 0.932s
40x40 : ?
60x6 : StackOverFlow
(Il faut savoir que je fais tourner mon programme sur une VM avec moins de 4 Mo de RAM)

Cette fois-ci les résultats sont beaucoup plus linéaire et le facteur chance disparait.
