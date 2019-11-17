import scala.{Array=>$}
object Sudoku{
  val table = $(
    $(5, 3, 0, 0, 7, 0, 0, 0, 0),
    $(6, 0, 0, 1, 9, 5, 0, 0, 0),
    $(0, 9, 8, 0, 0, 0, 0, 6, 0),
    $(8, 0, 0, 0, 6, 0, 0, 0, 3),
    $(4, 0, 0, 8, 0, 3, 0, 0, 1),
    $(7, 0, 0, 0, 2, 0, 0, 0, 6),
    $(0, 6, 0, 0, 0, 0, 2, 8, 0),
    $(0, 0, 0, 4, 1, 9, 0, 0, 5),
    $(0, 0, 0, 0, 8, 0, 0, 7, 9)
  )
  def main(args : Array[String]) : Unit = {
     println(table.map(_.mkString(" ")).mkString("\n") + "\n")
     parcours(table) match{
       case Some(s) => println(s.map(_.mkString(" ")).mkString("\n"))
       case None => println("Pas de solution")
     }
  }
  def parcours(t_ : Array[Array[Int]], i_ : Int = 0) : Option[Array[Array[Int]]] = {
    ((i_.toDouble / 9).asInstanceOf[Int],((i_.toDouble / 9).asInstanceOf[Int].toDouble - (i_.toDouble / 9)).toString.concat("00").charAt(3).toString.toInt) match {
      case (9,0) => Some(t_)
      case (x,y) if (t_(x)(y) != 0) => parcours(t_, i_ + 1)
      case (x,y) => (1 to 9).diff(((0 until 9).map((x,_)) ++ (0 until 9).map((_,y)) ++ (0 until 9).map(n => (x - x % 3 + n / 3, y - y % 3 + n % 3))).toSet.map((e : (Int,Int)) => t_(e._1)(e._2)).toSet.toList).collectFirst(Function.unlift((k : Int) => parcours(t_.updated(x, t_(x).updated(y,k)), i_ + 1)))
    }
  }
}
