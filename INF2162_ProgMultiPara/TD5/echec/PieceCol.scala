class PieceCol(etiquette_ : String, codeAnsi_ : String) extends Piece{
  var etiquette = etiquette_
  var codeAnsi = codeAnsi_
  var length = etiquette_.length
  def this(etiquette_ : String) = this(etiquette_, (Ansi.code("fblack"))+(Ansi.code("white")))
  override def toString() : String = (Ansi.code("fblack"))+this.codeAnsi+this.etiquette+(Ansi.reset)
  def getSize() : Int = this.length
}
object PieceCol{
  def apply(etiquette_ : String) : PieceCol = new PieceCol(etiquette_)
  def apply(etiquette_ : String, codeAnsi_ : String) : PieceCol = new PieceCol(etiquette_,codeAnsi_)
}
object Cavalier{
  def apply() : Option[PieceCol] ={
    Some(new PieceCol("caval"))
  }
}
object Dame{
  def apply() : Option[PieceCol] ={
    Some(new PieceCol("dame", Ansi.code("red")))
  }
}
object Fou{
  def apply() : Option[PieceCol] ={
    Some(new PieceCol("fou", Ansi.code("green")))
  }
}
object Pion{
  def apply() : Option[PieceCol] ={
    Some(new PieceCol("dame", Ansi.code("cyan")))
  }
}
object Rien{
  def apply() : Option[PieceCol] ={
    None
  }
}
