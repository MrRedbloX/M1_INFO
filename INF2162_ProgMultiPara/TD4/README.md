TD4 Anagramme par COGOLUEGNES Charles
Execution : scala Anagramme.jar

Formule pour avoir le nombre d'anagrammes : N!/r1!/r2!/../rn!
Avec N le nombre de lettre dans le mot, n un certain nombre de lettre se répétant r fois
Ex : "aaabbbcc", nbAnagrammes = 8!/3!/3!!2! = 560

Dans le programme nous avons deux mots : "abcdefg" et "elephant"
Pour le premier on a 5040 anagrammes = 7!
Pour le second on a 20160 anagrammes = 8!/2!(la lettre "e" se répète 2 fois)

Le programme ne renvoie pas les doublons

Explication de la fonction récursive :
On boucle sur chaque lettre du mot, et on construit un "sous-mot" contenant toutes les lettres sauf la lettre actuelle (les doublons sont gérés malgré le replaceFirst).
On appelle la fonction récursive sur ce "sous-mot" jusqu'à qu'il ne reste qu'une lettre (en effet on enlève une lettre à chaque appel).
Pour finir on va concatener la lettre actuelle avec tout les mots renvoyés par la fonction récursive. 
