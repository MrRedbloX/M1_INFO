import scala.{Array=>$}
import scala.collection.mutable.ListBuffer
object Fonctionnel{
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
     printTable(table)
     println()
     val ntable = table.updated(4, table(4).updated(5,9))
     printTable(ntable)
     println()
     println(parcours_1())
     println(parcours_1(42))
     println(parcours_1(80))
     parcours_8(table) match{
       case Some(s) => printTable(s)
       case None => println("Pas de solution")
     }
  }
  def printTable(table_ : Array[Array[Int]]) : Unit = println(table_.map(_.mkString(" ")).mkString("\n"))
  def parcours_1(i_ : Int = 0) : Tuple2[Int,Int] = {
    val x = (i_.toDouble / 9).asInstanceOf[Int]
    (x,(x.toDouble - (i_.toDouble / 9)).toString.concat("00").charAt(3).toString.toInt)
  }
  def parcours_2(i_ : Int = 0) : Unit = {
    parcours_1(i_) match {
      case (8,8) => println("derniere case")
      case (x,y) => {
        println(s"$i_ -> ($x,$y)")
        parcours_2(i_ + 1)
      }
    }
  }
  def parcours_3(i_ : Int = 0) : Unit = {
    parcours_1(i_) match {
      case (9,0) => println("on arrete, cette case ne doit pas être analysée")
      case (x,y) => {
        println(s"$i_ -> ($x,$y)")
        parcours_3(i_ + 1)
      }
    }
  }
  def parcours_4(i_ : Int = 0) : Unit = {
    def ligne(i_ : Int) : Array[(Int,Int)] = (0 until 9).map(e => (i_,e)).toArray
    def colonne(i_ : Int) : Array[(Int,Int)] = (0 until 9).map(e => (e,i_)).toArray
    def voisins(xy_ : (Int,Int)) : Array[(Int,Int)] = (0 until 9).map(e =>(xy_._1 - xy_._1 % 3 + e / 3, xy_._2 - xy_._2 % 3 + e % 3)).toArray
    def indiceAVoir(xy_ : (Int,Int)) : List[(Int,Int)] = (ligne(xy_._1) ++ colonne(xy_._2) ++ voisins(xy_)).toSet.toList
    parcours_1(i_) match {
      case (9,0) => println("stop ! une case de trop ")
      case (x,y) => {
        println(s"$i_ -> ($x,$y)")
        println(indiceAVoir((x,y)))
        parcours_4(i_ + 1)
      }
    }
  }
  def parcours_5(i_ : Int = 0) : Unit = {
    def ligne(i_ : Int) : Array[(Int,Int)] = (0 until 9).map(e => (i_,e)).toArray
    def colonne(i_ : Int) : Array[(Int,Int)] = (0 until 9).map(e => (e,i_)).toArray
    def voisins(xy_ : (Int,Int)) : Array[(Int,Int)] = (0 until 9).map(e =>(xy_._1 - xy_._1 % 3 + e / 3, xy_._2 - xy_._2 % 3 + e % 3)).toArray
    def indiceAVoir(xy_ : (Int,Int)) : List[(Int,Int)] = (ligne(xy_._1) ++ colonne(xy_._2) ++ voisins(xy_)).toSet.toList
    def nombreDejaPris(t_ : Array[Array[Int]], xy_ : (Int,Int)) : List[Int] = indiceAVoir(xy_).map(e => t_(e._1)(e._2))
    parcours_1(i_) match {
      case (9,0) => println("stop")
      case (x,y) => {
        println(s"$i_ -> ($x,$y)")
        println("deja pris : " + nombreDejaPris(table,(x,y)))
        parcours_5(i_ + 1)
      }
    }
  }
  def parcours_6(i_ : Int = 0) : Unit = {
    def ligne(i_ : Int) : Array[(Int,Int)] = (0 until 9).map(e => (i_,e)).toArray
    def colonne(i_ : Int) : Array[(Int,Int)] = (0 until 9).map(e => (e,i_)).toArray
    def voisins(xy_ : (Int,Int)) : Array[(Int,Int)] = (0 until 9).map(e =>(xy_._1 - xy_._1 % 3 + e / 3, xy_._2 - xy_._2 % 3 + e % 3)).toArray
    def indiceAVoir(xy_ : (Int,Int)) : List[(Int,Int)] = (ligne(xy_._1) ++ colonne(xy_._2) ++ voisins(xy_)).toSet.toList
    def nombreDejaPris(t_ : Array[Array[Int]], xy_ : (Int,Int)) : List[Int] = indiceAVoir(xy_).map(e => t_(e._1)(e._2))
    parcours_1(i_) match {
      case (9,0) => println("derniere case")
      case (x,y) => {
        println(s"$i_ -> ($x,$y)")
        val aEssayer = (1 until 9).diff(nombreDejaPris(table,(x,y))).toList
        println("a essayer : " + aEssayer)
        parcours_6(i_ + 1)
      }
    }
  }
  def parcours_7(t_ : Array[Array[Int]], i_ : Int = 0) : Option[Array[Array[Int]]] = {
    parcours_1(i_) match {
      case (9,0) => Some(t_)
      case (x,y) => {
        parcours_7(t_.updated(x, t_(x).updated(y,i_)), i_ + 1)
      }
    }
  }
  def parcours_8(t_ : Array[Array[Int]], i_ : Int = 0) : Option[Array[Array[Int]]] = {
    def ligne(i_ : Int) : Array[(Int,Int)] = (0 until 9).map(e => (i_,e)).toArray
    def colonne(i_ : Int) : Array[(Int,Int)] = (0 until 9).map(e => (e,i_)).toArray
    def voisins(xy_ : (Int,Int)) : Array[(Int,Int)] = (0 until 9).map(e =>(xy_._1 - xy_._1 % 3 + e / 3, xy_._2 - xy_._2 % 3 + e % 3)).toArray
    def indiceAVoir(xy_ : (Int,Int)) : List[(Int,Int)] = (ligne(xy_._1) ++ colonne(xy_._2) ++ voisins(xy_)).toSet.toList
    def nombreDejaPris(t_ : Array[Array[Int]], xy_ : (Int,Int)) : List[Int] = indiceAVoir(xy_).map(e => t_(e._1)(e._2))
    parcours_1(i_) match {
      case (9,0) => Some(t_)
      case (x,y) if (t_(x)(y) != 0) => parcours_8(t_, i_ + 1)
      case (x,y) => {
        val aEssayer = (1 to 9).diff(nombreDejaPris(t_,(x,y))).toList
        if(aEssayer.isEmpty) None
        else{
          val ts = aEssayer.map(e => parcours_8(t_.updated(x, t_(x).updated(y,e)), i_ + 1)).filterNot(_ == None)
          if(ts.length > 0) ts(0) else None
        }
      }
    }
  }
}
