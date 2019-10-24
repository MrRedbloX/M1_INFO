object Main{
  def main(args : Array[String]) : Unit ={
    val euler = new CavalierEuler(6)
    val timeStart = System.currentTimeMillis()
    euler.controller(0,0)
    val timeEnd = System.currentTimeMillis()
    val time : Double = ((timeEnd-timeStart).asInstanceOf[Double])/1000
    println(s"Time : $time s")
    println(euler)
  }
}
