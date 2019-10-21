object Main{
  def main(args : Array[String]) : Unit ={
    println("coucou"+Ansi.code("fBlue")+Ansi.code("red")+" abcdef "+Ansi.reset)
  }
}
