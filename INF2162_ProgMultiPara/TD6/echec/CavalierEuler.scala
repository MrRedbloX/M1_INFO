import util.control.Breaks._
class CavalierEuler(cote_ : Int){
  var cote = cote_
  var vue = new Echiquier[PieceCol](this.cote)
  var modele = Array.fill[Int](this.cote,this.cote)(0)
  def controller(xyDepart_ : Tuple2[Int,Int]) : Unit ={
    var dansEchiquier : PartialFunction[Tuple2[Int,Int],Tuple2[Int,Int]] ={
      case e if (e._1 >= 0 && e._1 < this.cote && e._2 >= 0 && e._2 < this.cote) => e
    }
    var pasDejaPassePar : PartialFunction[Tuple2[Int,Int],Tuple2[Int,Int]] ={
      case e if(this.modele(e._1)(e._2) == 0) => e
    }
    def deplacementsCavalier (xy_ : Tuple2[Int,Int]) : List[Tuple2[Int,Int]] ={
      var ret = List[Tuple2[Int,Int]]()
      ret = (xy_._1-2,xy_._2+1) :: ret
      ret = (xy_._1-2,xy_._2-1) :: ret
      ret = (xy_._1-1,xy_._2+2) :: ret
      ret = (xy_._1-1,xy_._2-2) :: ret
      ret = (xy_._1+1,xy_._2+2) :: ret
      ret = (xy_._1+1,xy_._2-2) :: ret
      ret = (xy_._1+2,xy_._2+1) :: ret
      ret = (xy_._1+2,xy_._2-1) :: ret
      ret collect dansEchiquier collect pasDejaPassePar
    }
    var heuristique = Array.fill(this.cote,this.cote)(0)
    heuristique = Array.tabulate(this.cote,this.cote)((x,y) => deplacementsCavalier((x,y)).size)
    def trouveDeplacementsCavalier(xy_ : Tuple2[Int,Int]) : List[Tuple2[Int,Int]] = deplacementsCavalier(xy_) sortBy(e => heuristique(e._1)(e._2))
    def trouvePositions(xy_ : Tuple2[Int,Int], etape_ : Int) : Boolean ={
      var ret : Boolean = true
      heuristique = Array.tabulate(this.cote,this.cote)((x,y) => deplacementsCavalier((x,y)).size)
      var deplacements = trouveDeplacementsCavalier(xy_)
      this.modele(xy_._1)(xy_._2) = etape_
      if(deplacements.size > 0){
        ret = false
        var i = 0
        while(i < deplacements.size && !ret){
          ret = trouvePositions(deplacements(i), etape_ + 1)
          i += 1
        }
      }
      else{
        if(!this.modele.flatten.contains(0)) ret = true
        else ret = false
      }
      if(!ret) this.modele(xy_._1)(xy_._2) = 0
      ret
    }
    def synchroniseVueAuModele() : Unit ={
      for(i <- 0 to this.cote-1; j <- 0 to this.cote-1) vue((i,j)) = Some(PieceCol(this.modele(i)(j).toString))
    }
    this.modele(xyDepart_._1)(xyDepart_._2) = 1
    if(trouvePositions(xyDepart_,1)) println("Une solution a été trouvée.")
    else println("Pas de solution au problème.")
    synchroniseVueAuModele()
    //heuristique foreach {e => e foreach print _; println}
  }
  override def toString() : String = this.vue.toString
}
