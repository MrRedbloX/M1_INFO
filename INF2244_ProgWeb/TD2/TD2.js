var canvas = document.getElementById("dessin")
var cahier = canvas.getContext("2d")
var hauteur = canvas.height
var largeur = canvas.width
var btnX = 125
var btnY = 40
var arrPoints = []
var pointChat = 0 //Voir accès tableau
var enableClicks = true

function dessineEcran(){
	cahier.font = "15px Helvetica"
	cahier.fillStyle = "yellow"
	cahier.fillRect(0,0,hauteur,largeur)
	cahier.fillStyle = "red"
	cahier.fillRect(0,0,btnX,btnY)
	cahier.fillStyle = "black"
	cahier.fillText("Efface", (btnX/2)-20, (btnY/2)+4)
	cahier.fillStyle = "red"
	cahier.fillRect(btnX+15,0,btnX,btnY)
	cahier.fillStyle = "black"
	cahier.fillText("Ligne", (btnX/2)+btnX, (btnY/2)+4)
	cahier.fillStyle = "red"
	cahier.fillRect((btnX*2)+30,0,btnX,btnY)
	cahier.fillStyle = "black"
	cahier.fillText("Animation", (btnX/2)+btnX*2, (btnY/2)+4)
}

function captureClick(event){
	if(enableClicks){
		var x = event.pageX - event.target.offsetLeft
		var y = event.pageY - event.target.offsetTop
		if(y <= btnY){
			if(x <= btnX){
				 dessineEcran()
				 arrPoints = []
				 pointChat = 0
			 }
			else if(x <= btnX*2+15) dessineLignes()
			else if(x <= btnX*3+30){
				enableClicks = false
				var chatX = arrPoints[0][0]
				var chatY = arrPoints[0][1]
				pointChat = 0
				dessineEcran()
				dessineLignes()
				dessineChat(chatX,chatY)
			 }
		}
		else if(x <= hauteur && y <= largeur){
			arrPoints.push([x,y])
			//console.log(arrPoints)
			cahier.font = "25px Helvetica"
			cahier.fillStyle = "blue"
			cahier.fillText(".",x,y)
		}
	}
}

function dessineLignes(){
	if(arrPoints.length > 1){
		cahier.beginPath()
		for(var coord in arrPoints){
			if(coord < arrPoints.length - 1){
				var coordPlus = parseInt(coord) + 1
				var coordMoveTo = arrPoints[coord]
				var coordLineTo = arrPoints[coordPlus]
				cahier.moveTo(coordMoveTo[0],coordMoveTo[1])
				cahier.lineTo(coordLineTo[0],coordLineTo[1])
			}
		}
		cahier.stroke()
	}
}

function dessineChat(x,y){
	dessineLignes()
	var img = document.getElementById("chatmarche");
	cahier.drawImage(img, x-(img.height/2), y-(img.width/2));
	document.getElementById("miaou").play()
	var id = setInterval(dessineChatPointSuivant,1000)
	pointChat++
	function dessineChatPointSuivant(){
		if(pointChat == arrPoints.length-1){
			dessineEcran()
			dessineLignes()
			img = document.getElementById("chatdort");
			var nextX = arrPoints[pointChat][0]
			var nextY = arrPoints[pointChat][1]
			cahier.drawImage(img, nextX-(img.height/2), nextY -(img.width/2))
			enableClicks = true
			clearInterval(id)
		}
		else{
			dessineEcran()
			dessineLignes()
			var nextX = arrPoints[pointChat][0]
			var nextY = arrPoints[pointChat][1]
			cahier.drawImage(img, nextX-(img.height/2), nextY -(img.width/2))
			pointChat++
		}
	}
}

document.addEventListener("click", captureClick)
dessineEcran()
