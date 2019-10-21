class PieceCol(etiquette_ : String, codeAnsi_ : String){
  val defaultBgColor = Ansi.code("fblack")
  val etiquette = etiquette_
  val codeAnsi = codeAnsi_
  def this(etiquette_ : String){
    this(etiquette_, Ansi.code("white")+Ansi.code("fblack"))
  }

  override def toString() : String ={
    this.codeAnsi+this.defaultBgColor+this.etiquette+(Ansi.reset)
  }
}
