import scala.reflect.ClassTag
class Echiquier[Piece : ClassTag](cote_ : Int){
  val plateau = Array.ofDim[Option[Piece]](cote_,cote_)

  def placerEn(piece_ : Option[Piece], x_ : Int, y_ : Int) : Unit = this.plateau(x_)(y_) = piece_
}
