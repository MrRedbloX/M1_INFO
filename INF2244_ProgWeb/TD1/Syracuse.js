//Calcul basique d'un nombre de syracuse
function syracuse(number_){
  if(number_ % 2 == 0) return number_/2
  else return (number_*3)+1
}

//Permet d'afficher la suite de syracuse à partir d'un nombre donné
function displaySyracuse(nb_){
  var canvas = document.getElementById("dessin")
  var cahier = canvas.getContext("2d")
  var xPos = 1
  var nb = nb_
  cahier.clearRect(0, 0, canvas.width, canvas.height)
  cahier.font = "10px Helvetica"
  cahier.fillStyle = "blue"
  cahier.beginPath()
  for(var i =0; i < 1000; i++){
    cahier.fillText(nb,xPos,canvas.height-nb)
    cahier.moveTo(xPos,canvas.height-nb)
    xPos += 5
    nb = syracuse(nb)
    cahier.lineTo(xPos,canvas.height-nb)
  }
  cahier.stroke()
}

//Permet de mettre à jour le dessin avec un nouveau nombre
document.getElementById("button").onclick = function(){
  var nb = parseInt(document.getElementById("number").value, 10)
  displaySyracuse(nb)
}
displaySyracuse(157)

//Représente le temps de vol pour le nombre de 1 à un nombre donné
//On part sur le principe qu'il faut 0 itérations pour les nombres 4, 2 et 1
function displayTempsVol(nb_){
  var canvas = document.getElementById("dessin2")
  var xPos = 1
  var cahier = canvas.getContext("2d")
  cahier.font = "10px Helvetica"
  var nbIt = 0
  for(var i = 1; i <= nb_; i++){
    var res = i
    nbIt = 0
    while(!(res in [4,2,1])){ //Qui n'est pas 4, 2 ou 1
      res = syracuse(res)
      nbIt++
    }
    cahier.fillStyle = "red";
    cahier.fillRect(xPos,canvas.height-nbIt,7,canvas.height-1)
    cahier.fillStyle = "black";
    cahier.fillText(nbIt,xPos,canvas.height-nbIt)
    xPos += 7
  }
}
displayTempsVol(500)
