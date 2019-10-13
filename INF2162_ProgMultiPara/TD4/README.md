TD4 Anagramme par COGOLUEGNES Charles.
Compilation : scalac Anagramme.scala
Exécution : scala Anagramme

Formule pour avoir le nombre d'anagrammes : N!/r1!/r2!/../rn!
Avec N le nombre de lettre dans le mot, n un certain nombre de lettre se répétant r fois.
Ex : "aaabbbcc", nbAnagrammes = 8!/3!/3!!2!.

Dans le programme nous avons deux mots : "abcdefg" et "elephant".
Pour le premier on a 5040 anagrammes = 7!.
Pour le second on a 20160 anagrammes = 8!/2!(la lettre "e" se répète 2 fois).

Le programme ne renvoie pas les doublons.
