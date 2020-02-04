var Utility = {
  randInt : function(min_,max_){
    return min_ + Math.floor(Math.random() * Math.floor(max_));
  }
}
class Model {
  constructor(gridSize){
    this.gridSize = gridSize
    this.grid = []
    this.points = 0
    this.traceGrid = [[],[],[]]
    this.igrid = []
    this.cgrid = []
    this.collapsing = false
    this.loadingDone = false
  }
  deepCopy(arr_,index_){
    for(let i=0; i<this.gridSize; i++){
      arr_[i] = []
      for(let j=0; j<this.gridSize; j++){
        arr_[i][j] = this.grid[i][j]
      }
    }
    this.traceGrid[index_].push(arr_.slice())
  }
  initGrid(){

    for(let i= 0; i<this.gridSize; i++){
      this.grid[i] = []
      for(let j=0; j<this.gridSize; j++){
        this.grid[i][j] = Utility.randInt(1,5)
        if(i == this.gridSize -1 && j == this.gridSize -1){
          this.deepCopy(this.igrid,0)
          this.collapseAlignments()
        }
      }
    }
  }
  collapseAlignments(callback_){

    this.collapsing = false
    let ret = false
    for(let i=0; i<this.gridSize; i++){
      for(let j=0; j<this.gridSize; j++){
        if(this.grid[i][j] != 0){
          if(this.checkCollapse(i,j,true)) ret = true
        }
        if(i == this.gridSize-1 && j == this.gridSize-1){
          if(callback_ != undefined) {
            this.loadingDone = true
            callback_(ret,this.grid)
          }
          if(this.collapsing){
              this.deepCopy(this.cgrid,1)
              this.makeCandiesFall()
          }
        }
      }
    }
  }
  startCollapseVertical(i_,j_,direction_,value_){
    this.collapsing = true
    let i = i_
    while(i >= 0 && i < this.gridSize && this.grid[i][j_] == value_){
      this.grid[i][j_] = 0
      i = i + direction_
      this.points++
    }
  }
  startCollapseHorizontal(i_,j_,direction_,value_){
    this.collapsing = true
    let j = j_
    while(j >= 0 && j < this.gridSize && this.grid[i_][j] == value_){
      this.grid[i_][j] = 0
      j = j + direction_
      this.points++
    }
  }
  checkCollapse(i_,j_,collapse_){

    let ret = true
    if(j_-1 >= 0 && j_-2 >= 0 && this.grid[i_][j_] == this.grid[i_][j_-1] && this.grid[i_][j_] == this.grid[i_][j_-2]){
      if(collapse_) this.startCollapseHorizontal(i_,j_,-1,this.grid[i_][j_])
    }
    else if(j_+1 < this.gridSize && j_+2 < this.gridSize && this.grid[i_][j_] == this.grid[i_][j_+1] && this.grid[i_][j_] == this.grid[i_][j_+2]){
      if(collapse_) this.startCollapseHorizontal(i_,j_,1,this.grid[i_][j_])
    }
    else if(i_-1 >= 0 && i_-2 >= 0 && this.grid[i_][j_] == this.grid[i_-1][j_] && this.grid[i_][j_] == this.grid[i_-2][j_]){
      if(collapse_) this.startCollapseVertical(i_,j_,-1,this.grid[i_][j_])
    }
    else if(i_+1 < this.gridSize && i_+2 < this.gridSize && this.grid[i_][j_] == this.grid[i_+1][j_] && this.grid[i_][j_] == this.grid[i_+2][j_]){
      if(collapse_) this.startCollapseVertical(i_,j_,1,this.grid[i_][j_])
    }
    else ret = false
    return ret
  }
  makeCandiesFall(){

    for(let i=this.gridSize-2; i>=0; i--){
      for(let j=0; j<this.gridSize; j++){
        if(this.grid[i][j] != 0 && this.grid[i+1][j] == 0){
          let ii = i
          while(ii < this.gridSize-1 && this.grid[ii+1][j] == 0){
            this.grid[ii+1][j] = this.grid[ii][j]
            this.grid[ii][j] = 0
            ii = ii+1
          }
        }
        if(i == 0 && j == this.gridSize-1){
           this.refillCandies()
         }
      }
    }
  }
  refillCandies(){

    let needToFall = false
    for(let i=0; i<this.gridSize; i++){
      for(let j=0; j<this.gridSize; j++){
        if(this.grid[i][j] == 0){
          this.grid[i][j] = Utility.randInt(1,5)
        }
      }
    }
    this.collapseAlignments()
  }
  swap(i_,j_,pI_,pJ_,callback_){
    let ret = false
    if((Math.abs(pI_) == 1 && pJ_ == 0) || (Math.abs(pJ_) == 1 && pI_ == 0)){
      let ii = i_+pI_
      let jj = j_+pJ_
      if(ii < this.gridSize && ii >= 0 && jj < this.gridSize && jj >= 0){
        let tmp = this.grid[i_][j_]
        this.grid[i_][j_] = this.grid[ii][jj]
        this.grid[ii][jj] = tmp
        this.swapCallback =
        this.collapseAlignments(function(res,grid){
          if(res) callback_(true)
          else{
            grid[ii][jj] = grid[i_][j_]
            grid[i_][j_] = tmp
            callback_(false)
          }
        })
      }
    }
    //return ret
  }
  gameOver(){
    let ret = true
    let stop = false
    for(let i=0; i<this.gridSize; i++){
      for(let j=0; j<this.gridSize; j++){
        let tmp = this.grid[i][j]
        if(j-1 >= 0){
          this.grid[i][j] = this.grid[i][j-1]
          this.grid[i][j-1] = tmp
          if(this.checkCollapse(i,j,false)){
            ret = false
            this.grid[i][j-1] = this.grid[i][j]
            this.grid[i][j] = tmp
            stop = true
            break
          }
        }
        if(j+1 < this.gridSize){
          this.grid[i][j] = this.grid[i][j+1]
          this.grid[i][j+1] = tmp
          if(this.checkCollapse(i,j,false)){
            ret = false
            this.grid[i][j+1] = this.grid[i][j]
            this.grid[i][j] = tmp
            stop = true
            break
          }
        }
        if(i-1 >= 0){
          this.grid[i][j] = this.grid[i-1][j]
          this.grid[i-1][j] = tmp
          if(this.checkCollapse(i,j,false)){
            ret = false
            this.grid[i-1][j] = this.grid[i][j]
            this.grid[i][j] = tmp
            stop = true
            break
          }
        }
        if(i+1 < this.gridSize){
          this.grid[i][j] = this.grid[i+1][j]
          this.grid[i+1][j] = tmp
          if(this.checkCollapse(i,j,false)){
            ret = false
            this.grid[i+1][j] = this.grid[i][j]
            this.grid[i][j] = tmp
            stop = true
            break
          }
        }
      }
      if(stop) break
    }
    return ret
  }
}

class Candy{
  constructor(id_){
    this.id = id_
    this.currentPos = [0,0]
    this.destinationPos = [0,0]
    this.img = undefined
    this.isCollapse = false
    if(this.id == 1) this.imgPath = "./res/Blue.png"
    else if(this.id == 2) this.imgPath = "./res/Green.png"
    else if(this.id == 3) this.imgPath = "./res/Orange.png"
    else if(this.id == 4) this.imgPath = "./res/Red.png"
    else if(this.id == 5) this.imgPath = "./res/Yellow.png"
  }
  isMoving(){
    let ret = false
    if(this.currentPos[0] != this.destinationPos[0] || this.currentPos[1] != this.destinationPos[1]) ret = true
    return ret
  }
  setCurrentPos(x_,y_){
    this.currentPos[0] = x_
    this.currentPos[1] = y_
  }
  setDestinationPos(x_,y_){
    this.destinationPos[0] = x_
    this.destinationPos[1] = y_
  }
  move(by){
    if(by == undefined) by = 1
    if(this.destinationPos[0] - this.currentPos[0] > 0 ) this.currentPos[0] = this.currentPos[0] + by
    else if(this.destinationPos[0] - this.currentPos[0] < 0 ) this.currentPos[0] = this.currentPos[0] - by
    if(this.destinationPos[1] - this.currentPos[1] > 0 ) this.currentPos[1] = this.currentPos[1] + by
    else if(this.destinationPos[1] - this.currentPos[1] < 0 ) this.currentPos[1] = this.currentPos[1] - by
  }
}

class View{
  constructor(candySize_,canvasName_){
    this.candySize = candySize_
    this.viewGrid = []
    this.canvas = document.getElementById(canvasName_)
    this.context = this.canvas.getContext("2d")
    this.allowClick = true
    this.hasEltSelected = false
    this.selectedCandy = undefined
  }
  initViewGrid(gridModel_){
    for(let i=0; i<gridModel_.length; i++){
      this.viewGrid[i] = []
      for(let j=0; j<gridModel_[i].length; j++){
        this.viewGrid[i][j] = new Candy(gridModel_[i][j])
      }
    }
    this.initCanvas()
  }
  initCanvas(){
    this.canvas.height = this.viewGrid.length * this.candySize
    this.canvas.width = this.canvas.height
    var $this = this
    this.canvas.onmouseover = function(){
      $this.canvas.style.cursor = "pointer";
    }
    for(let i=0; i<this.viewGrid.length; i++){
      for(let j=0; j<this.viewGrid[i].length; j++){
        let img = new Image()
        img.src = this.viewGrid[i][j].imgPath
        img.onload = function(){
          $this.context.drawImage(img,i*$this.candySize,j*$this.candySize)
        }
        this.viewGrid[i][j].img = img
        this.viewGrid[i][j].setCurrentPos(i*this.candySize,j*this.candySize)
        this.viewGrid[i][j].setDestinationPos(i*this.candySize,j*this.candySize)
      }
    }
  }
  swapCandies(context_,fromCandy_,toCandy_,view_){
    fromCandy_.destinationPos = toCandy_.currentPos.slice()
    toCandy_.destinationPos = fromCandy_.currentPos.slice()
    view_.allowClick = false
    var t
    function swap(){
      t = setTimeout(function(){
          context_.clearRect(fromCandy_.currentPos[0], fromCandy_.currentPos[1], view_.candySize, view_.candySize)
          context_.clearRect(toCandy_.currentPos[0], toCandy_.currentPos[1], view_.candySize, view_.candySize)
          fromCandy_.move()
          toCandy_.move()
          context_.drawImage(fromCandy_.img,fromCandy_.currentPos[0],fromCandy_.currentPos[1])
          context_.drawImage(toCandy_.img,toCandy_.currentPos[0],toCandy_.currentPos[1])
          if(fromCandy_.isMoving() || toCandy_.isMoving())swap()
          else{
            view_.selectedCandy = undefined
            view_.allowClick = true
            view_.hasEltSelected = false
            clearTimeout(t)
          }
        },10)
    }
    swap()
  }
  collapseCandies(context_,listCandies_,view_,callback_,args){
    var t
    view_.allowClick = false
    function collapse(from_){
      t = setTimeout(function(){
        context_.fillStyle = "lightblue";
        for(let i=0; i<listCandies_.length; i++){
          context_.fillRect(listCandies_[i].currentPos[0]+from_,listCandies_[i].currentPos[1],1,view_.candySize)
        }
        if(from_+1 <= view_.candySize+1) collapse(from_+1)
        else{
          view_.allowClick = true
          clearTimeout(t)
          if(callback_ != undefined) callback_(args[0],args[1],args[2],args[3],args[4])
        }
      },5)
    }
    collapse(-1)
  }
  fallCandies(context_,listCandies_,view_,callback_,args_){
    var t
    view_.allowClick = false
    function fall(from_){
      t = setTimeout(function(){
        let areMoving = false
        for(let i=0; i<listCandies_.length; i++){
          context_.clearRect(listCandies_[i].currentPos[0], listCandies_[i].currentPos[1], view_.candySize, view_.candySize)
          listCandies_[i].move(Utility.randInt(1,5))
          context_.drawImage(listCandies_[i].img,listCandies_[i].currentPos[0],listCandies_[i].currentPos[1])
          if(listCandies_[i].isMoving()) areMoving = true
        }
        if(areMoving) fall()
        else{
          view_.allowClick = true
          clearTimeout(t)
          callback_(args_[0],args_[1])
        }
      },2)
    }
    fall()
  }
}

class Controller{
  constructor(){
    this.gridSize = 5
    this.candySize = 88
    this.model = new Model(this.gridSize)
    this.view = new View(this.candySize,"draw")
  }
  start(){
    this.model.initGrid()
    var t
    function waitForModel(model_){
      t = setTimeout(function(){
        if(!model_.loadingDone) waitForModel(model_)
      },10)
    }
    waitForModel(this.model)
    console.log(this.model.traceGrid)
    this.view.initViewGrid(this.model.traceGrid[0][0])
    this.handleSwap()
    this.usualTask(0,this)

  }
  usualTask(index_,control_){
    if(control_.model.traceGrid[1][index_] != undefined){
      let listToCollapse = []
      let listToFall = []
      let collapseList = control_.model.traceGrid[1][index_]
      for(let i=0; i<collapseList.length; i++){
        for(let j=collapseList.length-1; j>=0; j--){
          if(collapseList[i][j] == 0){
             listToCollapse.push(control_.view.viewGrid[i][j])
             control_.view.viewGrid[i][j].isCollapse = true
           }
           if(j+1 < collapseList.length && collapseList[i][j+1] == 0){
             let jj = j
             while(collapseList[i][jj+1] == 0 && jj < collapseList.length) jj = jj+1
             control_.view.viewGrid[i][j].destinationPos = control_.view.viewGrid[i][jj].currentPos.slice()
             collapseList[i][jj] = collapseList[i][j]
             collapseList[i][j] = 0
             if(!control_.view.viewGrid[i][j].isCollapse){
               if(listToFall.length == 0) listToFall.push(control_.view.viewGrid[i][j])
               else{
                 let isPresent = false
                 for(let k=0; k<listToFall.length; k++){
                   if(listToFall[k].currentPos[0] == control_.view.viewGrid[i][j].currentPos[0] && listToFall[k].currentPos[1] == control_.view.viewGrid[i][j].currentPos[1]){
                     isPresent = true
                     break
                   }
                 }
                 if(!isPresent) listToFall.push(control_.view.viewGrid[i][j])
               }
             }
           }
        }
      }
      for(let i=0; i<collapseList.length; i++){
        for(let j=0; j<collapseList.length; j++){
          if(collapseList[i][j] == 0){
            let newCandy = new Candy(control_.model.grid[i][j])
            control_.view.viewGrid[i][j] = newCandy
            newCandy.currentPos[0] = i * control_.candySize
            newCandy.currentPos[1] = -control_.candySize
            newCandy.destinationPos[0] = i * control_.candySize
            newCandy.destinationPos[1] = j * control_.candySize
            let img = new Image()
            img.src = newCandy.imgPath
            newCandy.img = img
            listToFall.push(newCandy)
           }
        }
      }
      control_.view.collapseCandies(control_.view.context,listToCollapse,control_.view,control_.view.fallCandies,[control_.view.context,listToFall,control_.view,control_.usualTask,[index_+1,control_]])
    }
  }
  handleSwap(){
    var $this = this
    this.view.canvas.onclick = function(event){
      if($this.view.allowClick){
        let x = event.pageX - event.target.offsetLeft
    		let y = event.pageY - event.target.offsetTop
        let hasBreak = false
        for(let i=0; i<$this.view.viewGrid.length; i++){
          for(let j=0; j<$this.view.viewGrid[i].length; j++){
            let candy = $this.view.viewGrid[i][j]
            let xCandy = candy.currentPos[0]
            let yCandy = candy.currentPos[1]
            if(x > xCandy && x < xCandy + $this.candySize && y > yCandy && y < yCandy + $this.candySize){
              if($this.view.hasEltSelected){
                if($this.view.selectedCandy[0].currentPos[0] == xCandy && $this.view.selectedCandy[0].currentPos[1] == yCandy) {
                  $this.view.context.clearRect(xCandy, yCandy, $this.candySize, $this.candySize)
                  $this.view.context.drawImage(candy.img,i*$this.candySize,j*$this.candySize)
                  $this.view.selectedCandy = undefined
                  $this.view.hasEltSelected = false
                }
                else{
                  $this.checkSwap(xCandy,yCandy,$this.view.selectedCandy[0].currentPos[0],$this.view.selectedCandy[0].currentPos[1],$this,function(res){
                    if(res){
                      $this.view.viewGrid[i][j] = $this.view.selectedCandy[0]
                      $this.view.viewGrid[$this.view.selectedCandy[1]][$this.view.selectedCandy[2]] = candy
                      $this.view.swapCandies($this.view.context,$this.view.selectedCandy[0],candy,$this.view)
                      $this.view.context.clearRect($this.view.selectedCandy[0].currentPos[0], $this.view.selectedCandy[0].currentPos[1], $this.view.candySize, $this.view.candySize)
                    }
                    else{
                      $this.view.context.clearRect(xCandy, yCandy, $this.candySize, $this.candySize)
                      $this.view.context.fillStyle = "red";
                      $this.view.context.fillRect(candy.currentPos[0],candy.currentPos[1],$this.candySize,$this.candySize)
                      $this.view.context.drawImage(candy.img,i*$this.candySize,j*$this.candySize)
                       setTimeout(function(){
                         $this.view.context.clearRect(xCandy, yCandy, $this.candySize, $this.candySize)
                         $this.view.context.drawImage(candy.img,i*$this.candySize,j*$this.candySize)
                       },50)
                     }
                  })
                }
              }
              else{
                console.log("select")
                $this.view.context.clearRect(xCandy, yCandy, $this.candySize, $this.candySize)
                $this.view.context.fillStyle = "lightgray";
                $this.view.context.fillRect(candy.currentPos[0],candy.currentPos[1],$this.candySize,$this.candySize)
                $this.view.context.drawImage(candy.img,i*$this.candySize,j*$this.candySize)
                $this.view.hasEltSelected = true
                $this.view.selectedCandy = [candy,i,j]
              }
              hasBreak = true
              break
            }
          }
          if(hasBreak) break
        }
      }
    }
  }
  checkSwap(x1_,y1_,x2_,y2_,control_,callback_){
    let k1 = x1_/control_.candySize
    let l1 = y1_/control_.candySize
    let k2 = x2_/control_.candySize
    let l2 = y2_/control_.candySize
    control_.model.swap(k1,l1,k2-k1,l2-l1,callback_)
  }
}

function testModel(){
  var model = new Model(5)
  model.initGrid()
  console.log(model.traceGrid)
  console.log(model.points)
  function testSwap(){
    console.log(model.grid)
    setTimeout(function(){
      if(model.gameOver()) console.log("GameOver")
      else{
        var i = parseInt(prompt("i"))
        var j = parseInt(prompt("j"))
        var pI = parseInt(prompt("pI"))
        var pJ = parseInt(prompt("pJ"))
        console.log(model.swap(i,j,pI,pJ))
        console.log(model.points)
      }
      if(confirm("Continue")) testSwap()
    },2000)
  }
  testSwap()
}
function testView(){
  var model = new Model(5)
  model.initGrid()
  var view = new View(88,"draw")
  view.initViewGrid(model.grid)
}
//testModel()
//testView()
var controller = new Controller()
controller.start()
