import scala.collection.immutable.HashSet
object Anagramme{

  def main(args : Array[String]) : Unit = {
     var res1 = trouveAnagrammesDe("abcdefg");
     println(s"Nombre des mots : ${res1.size}");
    // res1.foreach(println);
     var res2 = trouveAnagrammesDe("elephant");
     println(s"Nombre des mots : ${res2.size}");
     //res2.foreach(println);
  }

  def trouveAnagrammesDe(mot_ : String) : Array[String]  = {
    println(s"Recherche des anagrammes de '$mot_'");
    trouveAnagrammesDeRec(mot_);
  }
    def trouveAnagrammesDeRec(mot_ : String) : Array[String]  = {
          var ret: HashSet[String] = HashSet();
          if(mot_.size>1){
            for(i <- 0 to mot_.size-1){
                var motSuivant = mot_.substring(0, i)+mot_.substring(i+1);
                var retTmp = trouveAnagrammesDeRec(motSuivant);
                for(j<-0 to retTmp.size-1){
                  ret += s"${mot_(i)}${retTmp(j)}";
                }
            }
          }else{
            ret += mot_;
          }
          return ret.toArray;
    }
}
