Candy Crush par Charles COGOLUEGNES


Résultats :

Le Candy Crush est fonctionnel. Les directives du cahier des charges ont été respectées.
Le joueur se voit attribuer les points de la grille de départ lorsqu'elle contient des alignements de bonbons (choix).
La fonctionnalité GameOver détecte lorsqu'il n'y a plus d'alignement possible.
Lors de la séquence swap + collapse + fall + refill, le joueur ne peut pas avoir d'intéraction avec la grille.
Le bonbon sélectionné est sous fond grisé.
Un swap non valide est indiqué par un fond rouge sur le bonbon cible.


Remarques :

Le modèle contient une méthode revereGrid (techniquement c'est une transposée).
Cette dernière permet aux autres méthodes (makeCandiesFall et refillCandies) de calculer les positions relatives à la vue lors des déplacements de bonbons (car la position des bonbons à l'affichage est décalée).
Une solution plus élégante et plus efficace existe surement.


Problèmes :

Parfois il y a un décalage non expliqué entre la grille du modèle et la grille de la vue.
Ce problème est corrigé (voir "fix bug") à l'affichage (drawGrid).

Améliorations :

L'aspect graphique de l'application peut être amélioré grandement.
Une optimisation voire une réécriture de code est envisageable.
Par exemple, la méthode GameOver est lente car elle effectue toutes les possibilités de swap et regarde s'il y a des alignements.
Une solution meilleure serait de détecter un pattern (en L ou en V) de bonbons dans la grille pour savoir s'il reste des alignements.
