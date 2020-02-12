class Sudoku(startConfig_ : Array[Array[Int]]){
  var startConfig = startConfig_
  def solver() : Array[Array[Int]] = {
    var ret  = List[Array[Array[Int]]]()
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
      for(i <- fX until fX + 3; j <- fY until fY + 3){ //On regarde d'abord dans la grille 3x3
        if(workGrid(i)(j) == number_ ) possible = false
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
    def nextPositionsFrom(x_ : Int, y_ : Int) : (Int,Int) = { //On parcours la grille sous-grille (3x3) par sous-grille
      var (fX,fY) = identifyFrame(x_,y_)
      if(x_ + 1 == workGrid.length && y_ + 1 == workGrid.length) (-1,-1)
      else if(x_ + 1 > fX + 2 && y_ + 1 > fY + 2){
        if(y_ + 1 == workGrid.length) (x_ + 1,0)
        else (fX,y_ + 1)
      }
      else if(y_ + 1 > fY + 2) (x_ + 1,fY)
      else (x_,y_ + 1)
    }
    def fillXY(x_ : Int, y_ : Int) : Boolean = {
      var ret = false
      if(x_ == -1 || y_ == -1) ret = true //Ici on est arrivé à la fin du sudoku
      else{
        var (nX,nY) = nextPositionsFrom(x_,y_)
        var n = 1
        if(workGrid(x_)(y_) != 0) ret = fillXY(nX,nY) //On skip les positions déjà occupées
        else{
          while(!ret && n <= workGrid.length){
            if(isPossibleAt(n,x_,y_)){
              workGrid(x_)(y_) = n
              ret = fillXY(nX,nY)
            }
            n = n + 1
          }
          if(!ret) workGrid(x_)(y_) = 0 //On n'oublie de remettre à 0 les positions non valides
        }
      }
      ret
    }
    fillXY(0,0)
    workGrid
  }
}
import java.io.File
import java.io.PrintWriter
import scala.io.Source
import scala.util.Random
object Sudoku{
  def main(args : Array[String]) : Unit ={
    var gridName = scala.io.StdIn.readLine("Enter a grid name : ")
    var createGrid = scala.io.StdIn.readLine("Want to create a sudoku grid (Y/N) ? ")
    if(createGrid.toLowerCase == "y"){
      saveSudokuGridToFile(createSudokuGrid(), gridName)
      println("Grid created successfully")
    }
    else{
      var generateGrid = scala.io.StdIn.readLine("Want to generate a sudoku grid (Y/N) ? ")
      if(generateGrid.toLowerCase == "y"){
        var seedGrid = scala.io.StdIn.readLine("Enter the name of the seed grid : ")
        saveSudokuGridToFile(generateSudokuGrid(getGridFromFile(seedGrid)), gridName)
        println("Grid generate successfully")
      }
    }
    var grid = getGridFromFile(gridName)
    println("Original grid : ")
    printGrid(grid)
    var sudoku = new Sudoku(grid)
    var res = sudoku.solver()
    println("Solution :")
    printGrid(res)
  }
  def getGridFromFile(filename : String) : Array[Array[Int]] = {
    var ret = Array.fill(9,9)(0)
    var file = Source.fromFile(filename).getLines
    var i,j : Int = 0
    file.foreach(l => {
      l.foreach(e => {
        ret(i)(j) = e.toString.toInt
        j = j+1
      })
      i = i+1
      j=0
    })
    ret
  }
  def createSudokuGrid() : Array[Array[Int]] = {
    var ret = Array.fill(9,9)(0)
    for(i <- 0 until ret.length; j <- 0 until ret.length){
      var input = scala.io.StdIn.readLine(s"($i)($j) ? ")
      if(input != "") ret(i)(j) = input.toInt
    }
    ret
  }
  def getZeroOrE(e : Int, rand : Random) : Int = {
    var ret = e
    if(rand.nextInt(100) < 70) ret = 0
    ret
  }
  def generateSudokuGrid(seed : Array[Array[Int]]) : Array[Array[Int]] = {
    var ret = (new Sudoku(seed).solver())
    var random = new Random
    ret.map(l => l.map(getZeroOrE(_,random)))
  }
  def saveSudokuGridToFile(grid : Array[Array[Int]], filename : String) : Unit = {
    var file = new PrintWriter(new File(filename))
    grid.foreach(l => {
      l.foreach(e => {
        file.write(e.toString)
      })
      file.write("\n")
    })
    file.close()
  }
  def printGrid(grid : Array[Array[Int]]) : Unit = {
    var i,j : Int = 0
    println
    grid.foreach(l => {
      l.foreach(e => {
        print(e.toString + " ")
        j = j+1
        if(j == 3){
          print(" ")
          j = 0
        }
      })
      println
      i = i+1
      if(i == 3){
        println
        i = 0
      }
    })
  }
}
