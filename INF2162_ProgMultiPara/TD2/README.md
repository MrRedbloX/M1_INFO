TD2 Metro par COGOLUEGNES Charles.
Une dépendance vers la librairie jgrapht est nécessaire pour faire fonctionner le programme.
Dans la fonction main de la class Main, il y a une variable testActive permettant de choisir ou non d'exécuter les tests.
Par défaut dans le jar, elle est à true.
Afin de compliler et d'exécuter le code il faut utiliser les commandes suivantes :

Windows : >javac -cp ".;.\lib\jgrapht-core-1.3.0.jar" .\src\*.java -d .
          >java -cp ".;.\lib\jgrapht-core-1.3.0.jar" Main path

Linux : >javac -cp .:./lib/jgrapht-core-1.3.0.jar ./src/*.java -d .
        >java -cp .:./lib/jgrapht-core-1.3.0.jar Main path

Il faut préciser le chemin du fichier Metro.txt (ou autre nom) à l'exécution du programme (JAR ou .class).
