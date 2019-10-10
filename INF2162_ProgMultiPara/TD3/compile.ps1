Write-Host "Util Compilation"
javac .\src\util\*.java -d .\class\
Write-Host "Metro Compilation"
javac -cp ".;.\class" .\src\metro\*.java -d .\class\
Write-Host "Main Compilation"
javac -cp ".;.\class" .\src\Main.java -d .\
