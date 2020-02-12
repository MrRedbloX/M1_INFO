object Main{
  def main(args : Array[String]) : Unit ={
    var input = scala.io.StdIn.readLine("Cote de l'echiquer ? ").toInt
    val euler = new CavalierEuler(input)
    val timeStart = System.currentTimeMillis()
    euler.controller(0,0)
    val timeEnd = System.currentTimeMillis()
    val time : Double = ((timeEnd-timeStart).asInstanceOf[Double])/1000
    println(s"Temps : $time s")
    println(euler)
  }
}
