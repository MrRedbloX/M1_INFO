import scala.collection.immutable.HashSet
object Anagramme{
  def main(args : Array[String]) : Unit = {
     var res1 = trouveAnagrammesDe("abcdefg");
     println(s"Nombre des mots : ${res1.size}");
     //res1.foreach(println);
     var res2 = trouveAnagrammesDe("elephant");
     println(s"Nombre des mots : ${res2.size}");
     //res2.foreach(println);
  }
  def trouveAnagrammesDe(mot_ : String) : Array[String]  = {
    println(s"Recherche des anagrammes de '$mot_'");
    trouveAnagrammesDeRec(mot_);
  }
  def trouveAnagrammesDeRec(mot_ : String) : Array[String] = {
    var ret : HashSet[String] = HashSet();
    if(mot_.size > 1) mot_.foreach(l => trouveAnagrammesDeRec(mot_.replaceFirst(l.toString, "")).foreach(m => ret += s"$l$m"));
    else ret += mot_;
    return ret.toArray;
  }
}
