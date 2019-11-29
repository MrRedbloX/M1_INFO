import scala.{Array => $}
import scala.util.Random.{shuffle => shu}
import scala.math.{abs => abs}
class Perceptron(couches_ : $[Int]){
  var oI = (0 until couches_.length).map(i => new $[Double](couches_(i))).toArray
  var iI = (0 until couches_.length).map(i => new $[Double](couches_(i))).toArray
  var dI = (0 until couches_.length).map(i => new $[Double](couches_(i))).toArray
  var poids = (1 until couches_.length).map(i => Array.fill[Double](couches_(i), couches_(i-1))(1 - 2 * Math.random())).toArray
  def f(x_ : Double) : Double = 1/(1+Math.exp(-x_))
  def fp(x_ : Double) : Double = f(x_)*(1-f(x_))
  def prod(p1_ : $[Double], p2_ : $[Double]) : Double = {
    require(p1_.length == p2_.length, "Both vectors need to have to same length")
    p1_.zip(p2_).map{case (x,y) => x*y}.sum
  }
  def errQuad(p1_ : $[Double], p2_ : $[Double]) : Double = {
    require(p1_.length == p2_.length, "Both vectors need to have to same length")
    p1_.zip(p2_).map{ case (x,y) => (x-y)*(x-y)}.sum
  }
  def apply(in_ : $[Double]) : $[Double] = {
    var data = in_
    for(i <- 0 until poids.length){
      for(j <- 0 until poids(i).length){
        iI(i)(j) = prod(data, poids(i)(j))
        oI(i+1)(j) = f(iI(i)(j))
      }
      if(i + 1 < poids.length) data = oI(i + 1)
    }
    oI.last
  }
  def retroPropag(observe_ : $[Double], souhaite_ : $[Double], pas_ : Double = 0.1) : Unit = {
    for(i <- iI(iI.length-1).length-1 to 0 by -1){ //La couche de sortie
      dI(iI.length-1)(i) = 2*(observe_(i)-souhaite_(i))*fp(iI(iI.length-1)(i))
      for(j <- poids(iI.length-1)(i).length-1 to 0 by -1) poids(iI.length-1)(i)(j) = poids(iI.length-1)(i)(j) - pas_ * dI(iI.length-1)(i)*oI(iI.length-1)(j)
    }
    for(i <- iI.length-2 to 0 by -1){
      for(j <- iI(i).length-1 to 0 by -1){
        for(k <- dI(i).length-1 to j by -1) dI(i)(j) = dI(i)(j) + (dI(i)(k)*poids(i)(j)(k)*fp(iI(i)(j)))
        for(k <- poids(i)(j).length-1 to 0 by -1) poids(i)(j)(k) = poids(i)(j)(k) - pas_ * dI(i)(j)*oI(i)(k)
      }
    }
  }
  def erreur(ex_ : List[Tuple2[X,Y]]) : Double = ex_.map{ case (X(entree), Y(sortieSouhaitee)) => this.errQuad(sortieSouhaitee, this(entree))}.sum
  def apprendreUneFois(ex_ : List[Tuple2[X,Y]]) : Unit = shu(ex_).foreach(e => retroPropag(e._1.x_, e._2.y_))
}
object Perceptron{
  def apply(couches_ : Int*) : Perceptron = new Perceptron(couches_.toArray)
  def main(args : $[String]) : Unit = {
/*    val myP = Perceptron(3,1)
    println(myP($[Double](0,0,1)).toList)*/
    val tolerance = 0.1
    val dataAnd = List[Tuple2[X,Y]]((X($[Double](0,0,1)),Y($[Double](0))),(X($[Double](0,1,1)),Y($[Double](0))),(X($[Double](1,0,1)),Y($[Double](0))),(X($[Double](1,1,1)),Y($[Double](1))))
    val perceptron = Perceptron(3,1)
    var err = 1.0
    while(err > tolerance){
      perceptron.apprendreUneFois(dataAnd)
      val reel = dataAnd.flatMap(t => perceptron(t._1.x_)).toList
      val attendu = dataAnd.flatMap(t => t._2.y_).toList
      val tuple = reel.zip(attendu)
      err = abs(perceptron.erreur(tuple.map{ case (a,b) => (X($[Double](a)),Y($[Double](b)))}))
    }
  }
}
case class X(x_ : $[Double])
case class Y(y_ : $[Double])
