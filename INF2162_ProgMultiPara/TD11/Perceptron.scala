import scala.{Array => $}
import scala.util.Random.{shuffle => shu}
import scala.math.{abs => abs}
import scala.math.{exp => e}
import scala.math.{sin => s}
class Perceptron(couches_ : $[Int]){
  var oI = (0 until couches_.length).map(i => new $[Double](couches_(i))).toArray
  var iI = (0 until couches_.length).map(i => new $[Double](couches_(i))).toArray
  var dI = (0 until couches_.length).map(i => new $[Double](couches_(i))).toArray
  var poids = (1 until couches_.length).map(i => Array.fill[Double](couches_(i), couches_(i-1)){1 - 2 * Math.random()}).toArray
  def apply(in_ : $[Double]) : $[Double] = {
    require(in_.length == couches_(0), "Both vectors need to have to same length")
    for(i <- 0 until couches_(0)){ //On remplie les entrées
      this.iI(0)(i) = in_(i)
      this.oI(0)(i) = Perceptron.f(this.iI(0)(i))
    }
    for(i <- 1 until couches_.length){ //On propage l'activation
      for(j <- 0 until couches_(i)){
        this.iI(i)(j) = Perceptron.prod(this.oI(i-1), this.poids(i-1)(j)) //Les inputs sont décalés de 1 par rapport au output et aux poids
        this.oI(i)(j) = Perceptron.f(this.iI(i)(j))
      }
    }
    this.oI.last
  }
  def apprentissage(data_ : List[Tuple2[X,Y]], tolerance_ : Double = 0.05, nbItMax_ : Int = 50000) : Unit = {
    def retroPropag(observe_ : $[Double], souhaite_ : $[Double], pas_ : Double = 0.01) : Unit = {
      //Traitement de la première couche
      for(j <- 0 until couches_(couches_.length-1)){
        this.dI(couches_.length-1)(j) = 2 * (observe_(j)-souhaite_(j)) * Perceptron.fp(this.iI(couches_.length-1)(j)) //Le cas particulier de la couche de sortie
        for(k <- 0 until couches_(couches_.length-2)) this.poids(couches_.length-2)(j)(k) = this.poids(couches_.length-2)(j)(k) - (pas_ * this.dI(couches_.length-1)(j) * this.oI(couches_.length-2)(k)) //La correction des poids
      }
      //Traitement des couches suivantes
      for(i <- couches_.length-2 to 1 by -1){
        for(j <- 0 until couches_(i)){
          var s = 0.0
          for(h <- 0 until couches_(i+1)) s = s + this.dI(i+1)(h) * this.poids(i)(h)(j) //La somme des h
          this.dI(i)(j) = s * Perceptron.fp(this.iI(i)(j))
          for(k <- 0 until couches_(i-1)) this.poids(i-1)(j)(k) = this.poids(i-1)(j)(k) - (pas_ * this.dI(i)(j) * this.oI(i-1)(k)) //La correction des poids
        }
      }
    }
    def erreur(ex_ : List[Tuple2[X,Y]]) : Double = ex_.map{ case (X(entree), Y(sortieSouhaitee)) => Perceptron.errQuad(sortieSouhaitee, this(entree))}.sum
    def apprendreUneFois(ex_ : List[Tuple2[X,Y]], pas_ : Double) : Unit = shu(ex_).foreach(e => retroPropag(this(e._1.x_), e._2.y_, pas_))
    var nbIt = 0
    var err = 1.0
    println("Start")
    val filename = "erreur.dat"
    scala.tools.nsc.io.File(filename).writeAll("");
    var oldErr = 1.0
    var pas = 0.1
    while(err > tolerance_ && nbIt < nbItMax_){
	  if(nbIt % 100 == 0) oldErr = err
	  if(oldErr < err) pas = pas/10
      err = abs(erreur(data_))
      apprendreUneFois(data_,pas)
      //scala.tools.nsc.io.File(filename).appendAll(s"$nbIt $err\n")
      nbIt = nbIt + 1
      print(s"\rErreur : $err")
    }
    println(s"\nDone, nbIt : $nbIt")
  }
}
object Perceptron{
  def f(x_ : Double) : Double = fTanHyp(x_)
  def fp(x_ : Double) : Double = fpTanHyp(x_)
  def fSig(x_ : Double) : Double = 1/(1+e(-x_))
  def fpSig(x_ : Double) : Double = fSig(x_)*(1-fSig(x_))
  def fTanHyp(x_ : Double) : Double = (e(x_) - e(-x_)) / (e(x_) + e(-x_))
  def fpTanHyp(x_ : Double) : Double = (1 + fTanHyp(x_)) * (1 - fTanHyp(x_))
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
    val dataXOrTanHyp = List[Tuple2[X,Y]]((X($[Double](0,0,1)),Y($[Double](-1))),(X($[Double](0,1,1)),Y($[Double](1))),(X($[Double](1,0,1)),Y($[Double](1))),(X($[Double](1,1,1)),Y($[Double](-1))))
    val dataXOrSig = List[Tuple2[X,Y]]((X($[Double](0,0,1)),Y($[Double](0))),(X($[Double](0,1,1)),Y($[Double](1))),(X($[Double](1,0,1)),Y($[Double](1))),(X($[Double](1,1,1)),Y($[Double](0))))
    val perceptron = Perceptron(2,10,5,1)
    var dataSin = List[Tuple2[X,Y]]()
    for(i <- -6.28 to 6.28 by 0.1){
		dataSin = (X($[Double](i,1)),Y($[Double](s(i)))) :: dataSin
	}
    perceptron.apprentissage(dataSin, 0.00001, 300000)
    var res = 0.0
    scala.tools.nsc.io.File("sinapp.dat").writeAll("");
    for(i <- -6.28 to 6.28 by 0.2){
		res = perceptron($[Double](i,1)).head
		scala.tools.nsc.io.File("sinapp.dat").appendAll(s"$i $res\n")
	}
  }
}
case class X(x_ : $[Double])
case class Y(y_ : $[Double])
