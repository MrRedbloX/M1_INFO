import util.control.Breaks._
class CavalierEuler(cote_ : Int){
  var cote = cote_
  var vue = new Echiquier[PieceCol](this.cote)
  var modele = Array.fill[Int](this.cote,this.cote)(0)
  def controller(xyDepart_ : Tuple2[Int,Int]) : Unit ={
    def trouveDeplacementsCavalier(xy_ : Tuple2[Int,Int]) : List[Tuple2[Int,Int]] ={
      var ret = List[Tuple2[Int,Int]]()
      var dansEchiquier : PartialFunction[Tuple2[Int,Int],Tuple2[Int,Int]] ={
        case e if (e._1 >= 0 && e._1 < this.cote && e._2 >= 0 && e._2 < this.cote) => e
      }
      ret = (xy_._1-2,xy_._2+1) :: ret
      ret = (xy_._1-2,xy_._2-1) :: ret
      ret = (xy_._1-1,xy_._2+2) :: ret
      ret = (xy_._1-1,xy_._2-2) :: ret
      ret = (xy_._1+1,xy_._2+2) :: ret
      ret = (xy_._1+1,xy_._2-2) :: ret
      ret = (xy_._1+2,xy_._2+1) :: ret
      ret = (xy_._1+2,xy_._2-1) :: ret
      ret = ret collect dansEchiquier
      ret
    }
    def trouvePositions(xy_ : Tuple2[Int,Int], etape_ : Int) : Boolean ={
      var ret : Boolean = false
      var pasDejaPassePar : PartialFunction[Tuple2[Int,Int],Tuple2[Int,Int]] ={
        case e if(this.modele(e._1)(e._2) < etape_) => e
      }
      var listDeplacements = trouveDeplacementsCavalier(xy_) collect pasDejaPassePar
      if(listDeplacements.isEmpty){
        if(this.modele.flatten.contains(0)) ret = true
      }
      else{
        breakable{
          for(e <- listDeplacements){
            this.modele(e._1)(e._2) = this.modele(e._1)(e._2) + 1
            ret = trouvePositions(e, etape_ + 1)
            if(ret) break
          }
        }
      }
      ret
    }
    def synchroniseVueAuModele() : Unit ={
      for(i <- 0 to this.cote-1; j <- 0 to this.cote-1) vue((i,j)) = Some(PieceCol(this.modele(i)(j).toString))
    }
    this.modele(xyDepart_._1)(xyDepart_._2) = 1
    if(trouvePositions(xyDepart_,1)) println("Une solution a été trouvée.")
    else println("Pas de solution au problème.")
    synchroniseVueAuModele()
  }
  override def toString() : String = this.vue.toString

}
