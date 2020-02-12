object Anagrams{
  def main(args : Array[String]) : Unit = {
     var mot = "abcdefg"
     var res1 = anagrams(mot);
     println(s"Nombre d'anagrammes pour $mot : ${res1.size}");
     //res1.foreach(println);
     mot = "elephant"
     var res2 = anagrams(mot);
     println(s"Nombre d'anagrammes pour $mot : ${res2.size}");
     //res2.foreach(println);
  }
  def anagrams(mot_ : String) : Set[String] = {
    var ret = Set[String]();
    mot_.size match{
      case s if(s == 1) => ret += mot_
      case s => mot_.foreach(l => anagrams(mot_.replaceFirst(l.toString, "")).foreach(m => ret += s"$l$m"))
    }
    ret
  }
}
