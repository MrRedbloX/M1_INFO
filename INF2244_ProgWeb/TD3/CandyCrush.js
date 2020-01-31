var Modele = {
  grid : [],
  points : 0,
  traceGrid : [[],[],[],[]],
  gridSize : 3,
  igrid : [],
  cgrid : [],
  mgrid : [],
  rgrid : [],
  collapsing : false,
  randInt : function(min_,max_){
    return min_ + Math.floor(Math.random() * Math.floor(max_));
  },
  deepCopy : function(arr_,index_,callback_){
    for(let i=0; i<Modele.gridSize; i++){
      arr_[i] = []
      for(let j=0; j<Modele.gridSize; j++){
        arr_[i][j] = Modele.grid[i][j]
        if(i == Modele.gridSize-1 && j == Modele.gridSize-1){
          Modele.traceGrid[index_].push(arr_.slice())
          callback_()
         }
      }
    }
  },
  setGridSize : function(newGridSize_){
    Modele.gridSize = newGridSize_
  },
  initGrid : function(){

    for(let i= 0; i<Modele.gridSize; i++){
      Modele.grid[i] = []
      for(let j=0; j<Modele.gridSize; j++){
        Modele.grid[i][j] = Modele.randInt(1,5)
        if(i == Modele.gridSize -1 && j == Modele.gridSize -1){
          Modele.deepCopy(Modele.igrid,0,Modele.collapseAlignments)
        }
      }
    }
  },
  collapseAlignments : function(callback_){
    Modele.collapsing = false
    let ret = false
    for(let i=0; i<Modele.gridSize; i++){
      for(let j=0; j<Modele.gridSize; j++){
        if(Modele.grid[i][j] != 0){
          if(Modele.checkCollapse(i,j)) ret = true
        }
        if(i == Modele.gridSize-1 && j == Modele.gridSize-1){
          if(callback_ != undefined) {
            callback_(ret)
          }
          if(Modele.collapsing){
              Modele.deepCopy(Modele.cgrid,1,Modele.makeCandiesFall)
          }
        }
      }
    }
  },
  startCollapseVertical : function(i_,j_,direction_,value_){
    Modele.collapsing = true
    let i = i_
    while(i >= 0 && i < Modele.gridSize && Modele.grid[i][j_] == value_){
      Modele.grid[i][j_] = 0
      i = i + direction_
      Modele.points++
    }
  },
  startCollapseHorizontal : function(i_,j_,direction_,value_){
    Modele.collapsing = true
    let j = j_
    while(j >= 0 && j < Modele.gridSize && Modele.grid[i_][j] == value_){
      Modele.grid[i_][j] = 0
      j = j + direction_
      Modele.points++
    }
  },
  checkCollapse : function(i_,j_){
    let ret = true
    if(j_-1 >= 0 && j_-2 >= 0 && Modele.grid[i_][j_] == Modele.grid[i_][j_-1] && Modele.grid[i_][j_] == Modele.grid[i_][j_-2]){
      Modele.startCollapseHorizontal(i_,j_,-1,Modele.grid[i_][j_])
    }
    else if(j_+1 < Modele.gridSize && j_+2 < Modele.gridSize && Modele.grid[i_][j_] == Modele.grid[i_][j_+1] && Modele.grid[i_][j_] == Modele.grid[i_][j_+2]){
      Modele.startCollapseHorizontal(i_,j_,1,Modele.grid[i_][j_])
    }
    else if(i_-1 >= 0 && i_-2 >= 0 && Modele.grid[i_][j_] == Modele.grid[i_-1][j_] && Modele.grid[i_][j_] == Modele.grid[i_-2][j_]){
      Modele.startCollapseVertical(i_,j_,-1,Modele.grid[i_][j_])
    }
    else if(i_+1 < Modele.gridSize && i_+2 < Modele.gridSize && Modele.grid[i_][j_] == Modele.grid[i_+1][j_] && Modele.grid[i_][j_] == Modele.grid[i_+2][j_]){
      Modele.startCollapseVertical(i_,j_,1,Modele.grid[i_][j_])
    }
    else ret = false
    return ret
  },
  makeCandiesFall :  function(){

    for(let i=Modele.gridSize-2; i>=0; i--){
      for(let j=0; j<Modele.gridSize; j++){
        if(Modele.grid[i][j] != 0 && Modele.grid[i+1][j] == 0){
          let ii = i
          while(ii < Modele.gridSize-1 && Modele.grid[ii+1][j] == 0){
            Modele.grid[ii+1][j] = Modele.grid[ii][j]
            Modele.grid[ii][j] = 0
            ii = ii+1
          }
        }
        if(i == 0 && j == Modele.gridSize-1) Modele.deepCopy(Modele.mgrid,2,Modele.refillCandies)
      }
    }
  },
  refillCandies : function(){

    let needToFall = false
    for(let i=0; i<Modele.gridSize; i++){
      if(Modele.grid[0][i] == 0){
        needToFall = true
        Modele.grid[0][i] = Modele.randInt(1,5)
      }
    }
    if(needToFall) Modele.deepCopy(Modele.rgrid,3,Modele.makeCandiesFall)
    else Modele.deepCopy(Modele.rgrid,3,Modele.collapseAlignments)
  },
  swap : function(i_,j_,pI_,pJ_){
    let ret = false
    let ii = i_+pI_
    let jj = j_+pJ_
    console.log(ii)
    console.log(jj)
    if(ii < Modele.gridSize && ii >= 0 && jj < Modele.gridSize && jj >= 0){
      let tmp = Modele.grid[i_][j_]
      Modele.grid[i_][j_] = Modele.grid[ii][jj]
      Modele.grid[ii][jj] = tmp
      Modele.swapCallback =
      Modele.collapseAlignments(function(res){
        if(res) ret = true
        else{
          Modele.grid[ii][jj] = Modele.grid[i_][j_]
          Modele.grid[i_][j_] = tmp
        }
      })
    }
    return ret
  }
}

Modele.setGridSize(5)
Modele.initGrid()
console.log(Modele.traceGrid)
console.log(Modele.grid)
console.log(Modele.points)
function testSwap(){
  setTimeout(function(){
    var i = parseInt(prompt("i"))
    var j = parseInt(prompt("j"))
    var pI = parseInt(prompt("pI"))
    var pJ = parseInt(prompt("pJ"))
    console.log(Modele.swap(i,j,pI,pJ))
    console.log(Modele.grid)
    console.log(Modele.points)
    if(confirm("Continue")) testSwap()
  },2000)
}
testSwap()
