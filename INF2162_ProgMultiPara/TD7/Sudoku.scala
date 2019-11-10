class Sudoku(startConfig_ : Array[Array[Int]]){
  var startConfig = startConfig_
  def solver() : List[Array[Array[Int]]] = {
    var ret : List[Array[Array[Int]]] = List[Array[Array[Int]]]()
    var workGrid = startConfig.map(_.clone)
    def identifyFrame(x_ : Int, y_ : Int) : (Int,Int) = { //Donne la position de départ de la grille correspondante aux coordonnées données
      var x,y : Int = -1
      if(x_ < 3) x = 0
      else if(x_ >= 3 && x_ < 6) x = 3
      else x = 6
      if(y_ < 3) y = 0
      else if(y_ >= 3 && y_ < 6) y = 3
      else y = 6
      (x,y)
    }
    def isPossibleAt(number_ : Int, x_ : Int, y_ : Int) : Boolean = {
      var (fX, fY) = identifyFrame(x_,y_)
      var i,j : Int = 0
      var possible = true
      for(i <- fX to fX + 3; j <- fY to fY + 3){ //On regarde d'abord dans la grille 3x3
        if(Math.abs(workGrid(i)(j)) == number_ ) possible = false
      }
      if(possible){
        i = 0
        j = 0
        while(i < workGrid.length && j < workGrid.length && possible){ //Puis on cherche si le nombre est présent sur la ligne et colonne
          if(workGrid(x_)(i) == number_ || workGrid(j)(y_) == number_) possible = false
          i = i + 1
          j = j + 1
        }
      }
      possible
    }
    def fillNeighbours(number_ : Int,x_ : Int,y_ : Int) : Unit = { //On place -number sur toutes les cases voisines non remplies du cadre 3x3
      var (fX, fY) = identifyFrame(x_,y_)
      for(i <- fX to fX + 3; j <- fY to fY + 3){
        if(workGrid(i)(j) <= 0) workGrid(i)(j) = -number_
      }
    }
    def getPossibleNumbers(x_ : Int, y_ : Int) : Array[Int] = {
      var ret : Array[Int] = new Array[Int](3)
      ret
    }
    def nextPositionsFrom(x_ : Int, y_ : Int) : (Int,Int) = {
      if(x_ + 1 == workGrid.length && y_ + 1 == workGrid.length) (-1,-1)
      else if(y_ + 1 == workGrid.length) (x_ + 1,0)
      else (x_,y_ + 1)
    }
    def fillXY(x_ : Int, y_ : Int) : Boolean = {
      var (nextX,nextY) = nextPositionsFrom(x_,y_)
      if(workGrid(x_)(y_) <= 0){
        var numbers = getPossibleNumbers(x_,y_)
        var k = 0
        var possible = false
        while(k < numbers.length && !possible){
          var n = numbers(k)
          if(isPossibleAt(n,x_,y_)){
            workGrid(x_)(y_) = n
            fillNeighbours(n,x_,y_)
            possible = fillXY(nextX,nextY)
          }
          k = k+1
        }
        if(!possible) workGrid.foreach(l => l.filter(_ == -k).map(e => 0))
        possible
      }
      else if(nextX != -1) fillXY(nextX,nextY)
      else{
        if(workGrid.toSet.flatten.filter(_ <= 0).size > 0) false
        else true
      }
    }
    fillXY(0,0)
    ret
  }
}
object Sudoku{
  def main(args : Array[String]) : Unit ={
    
  }
}
