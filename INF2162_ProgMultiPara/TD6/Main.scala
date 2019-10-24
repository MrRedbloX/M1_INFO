object Main{
  def main(args : Array[String]) : Unit ={
    val euler = new CavalierEuler(6)
    euler.controller(0,0)
    println(euler)
  }
}
