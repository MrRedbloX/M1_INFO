TD3 Metro par COGOLUEGNES Charles.
Dans la fonction main de la class Main, il y a une variable testActive permettant de choisir ou non d'exécuter les tests.
Par défaut dans le jar, elle est à true.
Afin de compliler et d'exécuter le code il faut utiliser les commandes suivantes :

Windows : >javac .\src\util\*.java -d .\class
          >javac -cp ".;.\class" .\src\metro\*.java -d .\class
          >javac -cp ".;.\class" .\src\Main.java -d .\class
          >java -cp ".;.\class" Main .\res\Metro.txt

Linux : >javac ./src/util/*.java -d ./class
          >javac -cp .:./class ./src/metro/*.java -d ./class
          >javac -cp .:./class ./src/Main.java -d ./class
          >java -cp .:./class Main ./res/Metro.txt

Il faut préciser le chemin du fichier Metro.txt (ou autre nom) à l'exécution du programme (JAR ou .class).

Les méthodes addVecteur et suppVecteur de la classe Reseau sont en private car elles n'ont pas encore été testées.
