object Main{
  def main(args : Array[String]) : Unit ={
    //println("coucou"+Ansi.fblue+Ansi.red+" abcdef "+Ansi.reset)
    //val cavalier = new PieceCol("caval",Ansi.code("red"))
    //val cavalier = PieceCol("caval", Ansi.code("red"))
    //val cavalier = Some(PieceCol("caval"))
    //println(cavalier)
    //val echiquier = new Echiquier[PieceCol](8)
    //echiquier((2,2)) = cavalier
    //val Some(cavalierRet) = echiquier(2,2)
    //println(cavalierRet)
    println(test1)
    println(test2)
    var echiquierTest3 = test3
    println(echiquierTest3)
    println(echiquierTest3(5,2))
    println(echiquierTest3(3,5))
  }
  def test1() : Echiquier[PieceCol] ={
    val cavalier = Some(PieceCol("caval"))
    val echiquier = new Echiquier[PieceCol](8)
    echiquier((0,6)) = cavalier
    echiquier
  }
  def test2() : Echiquier[PieceCol] ={
    val echiquier = new Echiquier[PieceCol](8)
    echiquier((0,6)) = Some(PieceCol("caval"))
    echiquier((3,5)) = Some(PieceCol("dame",Ansi.red))
    echiquier((6,3)) = Some(PieceCol("fou",Ansi.green))
    echiquier((5,2)) = Some(PieceCol("pion",Ansi.cyan))
    echiquier
  }
  def test3() : Echiquier[PieceCol] ={
    val echiquier = new Echiquier[PieceCol](8)
    echiquier((0,6)) = Cavalier()
    echiquier((3,5)) = Dame()
    echiquier((6,3)) = Fou()
    echiquier((5,2)) = Pion()
    echiquier((5,2)) = Rien()
    echiquier
  }
}
