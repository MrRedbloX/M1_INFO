import scala.{Array => $}
import scala.util.Random.{shuffle => shu}
import scala.math.{abs => abs}
class Perceptron(couches_ : $[Int]){
  var oI = (0 until couches_.length).map(i => new $[Double](couches_(i))).toArray
  var iI = (0 until couches_.length).map(i => new $[Double](couches_(i))).toArray
  var dI = (0 until couches_.length).map(i => new $[Double](couches_(i))).toArray
  var poids = (1 until couches_.length).map(i => Array.fill[Double](couches_(i), couches_(i-1))(1 - 2 * Math.random())).toArray
  def apply(in_ : $[Double]) : $[Double] = {
    require(in_.length == couches_(0), "Both vectors need to have to same length")
    for(i <- 0 until couches_(0)){ //On remplie les entrées
      iI(0)(i) = in_(i)
      oI(0)(i) = Perceptron.f(iI(0)(i))
    }
    for(i <- 1 until couches_.length){ //On propage l'activation
      for(j <- 0 until couches_(i)){
        iI(i)(j) = Perceptron.prod(oI(i-1), poids(i-1)(j)) //Les inputs sont décalés de 1 par rapport aux autres
        oI(i)(j) = Perceptron.f(iI(i)(j))
      }
    }
    oI(couches_.length-1)
  }
  def retroPropag(observe_ : $[Double], souhaite_ : $[Double], pas_ : Double = 0.1) : Unit = {
    var cS = couches_.length - 1 //Couche de sortie
    for(i <- 1 until couches_.length){
      for(j <- 0 until couches_(i)){
        if(i == cS) dI(i)(j) = 2 * (observe_(j)-souhaite_(j)) * Perceptron.fp(iI(i)(j))
        else{
          var sum = 0.0
          for(h <- 0 until couches_(i+1)){
            sum = sum + (dI(i+1)(h) + poids(i)(h)(j)) //La somme des h
          }
          dI(i)(j) = sum * Perceptron.fp(iI(i)(j))
        }
        for(k <- couches_(i-1) - 1 to 0 by -1){
          poids(i-1)(j)(k) = poids(i-1)(j)(k) - (pas_ * dI(i)(j) * oI(i-1)(k)) //La correction des poids
        }
      }
    }
  }
  def erreur(ex_ : List[Tuple2[X,Y]]) : Double = ex_.map{ case (X(entree), Y(sortieSouhaitee)) => Perceptron.errQuad(sortieSouhaitee, this(entree))}.sum
  def apprendreUneFois(ex_ : List[Tuple2[X,Y]]) : Unit = shu(ex_).foreach(e => retroPropag(this(e._1.x_), e._2.y_))
}
object Perceptron{
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
  def apply(couches_ : Int*) : Perceptron = new Perceptron(couches_.toArray)
  def main(args : $[String]) : Unit = {
/*    val myP = Perceptron(3,1)
    println(myP($[Double](0,0,1)).toList)*/
    val tolerance = 0.05
    val dataAnd = List[Tuple2[X,Y]]((X($[Double](0,0,1)),Y($[Double](0))),(X($[Double](0,1,1)),Y($[Double](0))),(X($[Double](1,0,1)),Y($[Double](0))),(X($[Double](1,1,1)),Y($[Double](1))))
    val perceptron = Perceptron(3,1)
    var nbIt = 0
    var err = 1.0
    println("Start")
    while(err > tolerance){
      err = abs(perceptron.erreur(dataAnd))
      perceptron.apprendreUneFois(dataAnd)
      nbIt = nbIt + 1
      println(err)
    }
    println(s"Done, nbIt : $nbIt")
    println(perceptron($[Double](0,0,1)).toList)
    println(perceptron($[Double](0,1,1)).toList)
    println(perceptron($[Double](1,0,1)).toList)
    println(perceptron($[Double](1,1,1)).toList)
  }
}
case class X(x_ : $[Double])
case class Y(y_ : $[Double])
