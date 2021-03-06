import scala.reflect.ClassTag
class Echiquier[P <: Piece : ClassTag](cote_ : Int){
  var plateau = Array.ofDim[Option[Piece]](cote_,cote_)
  for( i <- 0 to cote_ - 1 ; j <- 0 to cote_ - 1) this.vider(i,j)
  def placerEn(piece_ : Option[Piece], x_ : Int, y_ : Int) : Unit = this.plateau(x_)(y_) = piece_
  def update(xy_ : Tuple2[Int,Int], piece_ : Option[Piece]) : Unit = this.placerEn(piece_,xy_._1,xy_._2)
  def apply(x_ : Int, y_ : Int) : Option[Piece] = this.plateau(x_)(y_)
  def vider(x_ : Int, y_ : Int) : Unit = this.plateau(x_)(y_) = None
  override def toString() : String = {
    var ret : String = ""
    var size = this.plateau.toStream.flatten.flatten.map(piece => piece.length).max //On récupère la taille max d'un mot
    ret = ret + Ansi.fblack+Ansi.black+(" "*(size-1))+Ansi.reset //On affiche l'échelle sur x
    for(i <- 0 to this.plateau.length-1) ret = ret + Ansi.fblack+Ansi.white+(" "*(size/4))+i.toString+(" "*(size-1))+Ansi.reset
    ret = ret + "\n"
    ret = ret + Ansi.fblack+Ansi.black+"  "+Ansi.reset
    for(i <- 0 to this.plateau.length-1) ret = ret + Ansi.fblue+Ansi.blue+(" "*(size/4))+(" "*size)+Ansi.reset //On affiche les espaces bleus
    ret = ret + Ansi.fblue+Ansi.blue+(" "*(size/4))+Ansi.reset+Ansi.fblack+Ansi.black+" "+Ansi.reset+"\n"
    for(i <- 0 to this.plateau.length-1){
      ret = ret + Ansi.fblack+Ansi.white+" "+i+Ansi.reset+Ansi.fblue+Ansi.blue+(" "*(size/4))+Ansi.reset //On affiche l'axe de y
      for(j <- 0 to this.plateau.length-1){
        this.plateau(i)(j) match{ //On affiche les pieces
          case None => ret = ret + Ansi.fblack+Ansi.black+(" "*size)+Ansi.reset
          case default => ret = ret + this.plateau(i)(j).get.toString+Ansi.fblack+Ansi.black+(" "*(size-this.plateau(i)(j).get.length))+Ansi.reset
        }
        ret = ret + Ansi.fblue+Ansi.blue+(" "*(size/4))+Ansi.reset
      }
      ret = ret + Ansi.fblack+Ansi.black+" "+Ansi.reset+"\n"+Ansi.fblack+Ansi.black+"  "+Ansi.reset
      for(k <- 0 to this.plateau.length-1) ret = ret + Ansi.fblue+Ansi.blue+(" "*(size/4))+(" "*size)+Ansi.reset
      ret = ret + Ansi.fblue+Ansi.blue+(" "*(size/4))+Ansi.reset+Ansi.fblack+Ansi.black+" "+Ansi.reset+"\n"
    }
    ret = ret + Ansi.fblack+Ansi.black+(" "*(size/2-1))+Ansi.reset
    for(i <- 0 to this.plateau.length-1) ret = ret + Ansi.fblack+Ansi.white+(" "*(size/4))+(" "*size)+Ansi.reset
    ret = ret + Ansi.fblack+Ansi.black+(" "*(size/2))+" "+Ansi.reset+"\n"
    ret
  }
}
