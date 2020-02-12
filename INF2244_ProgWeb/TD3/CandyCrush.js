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
    this.traceGrid = [[],[],[],[]]
    this.igrid = []
    this.cgrid = []
    this.rgrid = []
    this.mgrid = []
    this.collapsing = false
    this.loadingDone = true
  }
  resetTraceGrid(model_){
    model_.traceGrid = [[],[],[],[]]
  }
  deepCopy(arr_,index_,callback_,args_){
    for(let i=0; i<this.gridSize; i++){
      arr_[i] = []
      for(let j=0; j<this.gridSize; j++){
        arr_[i][j] = this.grid[i][j]
      }
    }
    if(index_ != undefined) this.traceGrid[index_].push(arr_.slice())
    if(callback_ != undefined) callback_(args_)
  }
  initGrid(){
    this.loadingDone = false
    this.igrid = []
    for(let i= 0; i<this.gridSize; i++){
      this.grid[i] = []
      for(let j=0; j<this.gridSize; j++){
        this.grid[i][j] = Utility.randInt(1,5)
        if(i == this.gridSize -1 && j == this.gridSize -1){
          this.deepCopy(this.igrid,0,this.collapseAlignments,this)
          //this.collapseAlignments()
        }
      }
    }
  }
  collapseAlignments(model_,callback_){
    model_.loadingDone = false
    model_.collapsing = false
    model_.cgrid = []
    let ret = false
    for(let i=0; i<model_.gridSize; i++){
      for(let j=0; j<model_.gridSize; j++){
        if(model_.grid[i][j] != 0){
          if(model_.checkCollapse(i,j,true)) ret = true
        }
        if(i == model_.gridSize-1 && j == model_.gridSize-1){
          if(callback_ != undefined) {
            model_.loadingDone = true
            callback_(model_,ret)
          }
          if(model_.collapsing){
              model_.deepCopy(model_.cgrid,1,model_.makeCandiesFall,model_)
          }
          else{
            model_.deepCopy(model_.cgrid,1)
            model_.loadingDone = true
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
      this.points = this.points + 1
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
  makeCandiesFall(model_){
    model_.mgrid = []
    model_.revereGrid(model_,function(model_,grid_){
      for(let i=model_.gridSize-2; i>=0; i--){
        for(let j=0; j<model_.gridSize; j++){
          if(grid_[i][j] != 0 && grid_[i+1][j] == 0){
            let ii = i
            let newPos = i+1
            while(ii < model_.gridSize-1 && grid_[ii+1][j] == 0){
              grid_[ii+1][j] = grid_[ii][j]
              grid_[ii][j] = 0
              newPos = ii+1
              ii = ii+1
            }
            model_.mgrid.push([j,i,j,newPos])
          }
          if(i == 0 && j == model_.gridSize-1){
             model_.traceGrid[2].push(model_.mgrid.slice())
             model_.refillCandies(model_)
           }
        }
      }
    })
  }
  refillCandies(model_){
    model_.rgrid = []
    model_.revereGrid(model_,function(){
      for(let i=0; i<model_.gridSize; i++){
        for(let j=0; j<model_.gridSize; j++){
          if(model_.grid[i][j] == 0){
            model_.grid[i][j] = Utility.randInt(1,5)
            model_.rgrid.push([i,j,model_.grid[i][j]])
          }
        }
      }
      model_.traceGrid[3].push(model_.rgrid.slice())
      model_.collapseAlignments(model_)
    })
  }
  swap(i_,j_,pI_,pJ_,callback_){
    let ret = false
    this.loadingDone = false
    if((Math.abs(pI_) == 1 && pJ_ == 0) || (Math.abs(pJ_) == 1 && pI_ == 0)){
      let ii = i_+pI_
      let jj = j_+pJ_
      if(ii < this.gridSize && ii >= 0 && jj < this.gridSize && jj >= 0){
        let tmpGrid = this.grid.slice()
        let tmp = tmpGrid[i_][j_]
        tmpGrid[i_][j_] = tmpGrid[ii][jj]
        tmpGrid[ii][jj] = tmp
        this.detectCollapse(this,tmpGrid,function(model_,res){
          if(res){
            model_.collapseAlignments(model_)
            callback_(true)
           }
          else{
            model_.loadingDone = true
            callback_(false)
          }
        })
      }
      else {
        callback_(false)
      }
    }
    else {
      callback_(false)
    }
  }
  gameOver(model_,callback_){
    model_.loadingDone = false
    let ret = true
    let stop = false
    let tmpGrid = []
    for(let i=0; i<model_.gridSize; i++){
      tmpGrid[i] = []
      for(let j=0; j<model_.gridSize; j++){
         tmpGrid[i][j] = model_.grid[i][j]
      }
    }
    for(let i=0; i<model_.gridSize; i++){
      for(let j=0; j<model_.gridSize; j++){
        let tmp = tmpGrid[i][j]
        if(j-1 >= 0){
          tmpGrid[i][j] = tmpGrid[i][j-1]
          tmpGrid[i][j-1] = tmp
          model_.detectCollapse(model_,tmpGrid,function(model_,res_){
            ret = res_
            tmpGrid[i][j-1] = tmpGrid[i][j]
            tmpGrid[i][j] = tmp
            if(ret) stop = true
          })
        }
        if(j+1 < model_.gridSize && !ret){
          tmpGrid[i][j] = tmpGrid[i][j+1]
          tmpGrid[i][j+1] = tmp
          model_.detectCollapse(model_,tmpGrid,function(model_,res_){
            ret = res_
            tmpGrid[i][j+1] = tmpGrid[i][j]
            tmpGrid[i][j] = tmp
            if(ret) stop = true
          })
        }
        if(i-1 >= 0 && !ret){
          tmpGrid[i][j] = tmpGrid[i-1][j]
          tmpGrid[i-1][j] = tmp
          model_.detectCollapse(model_,tmpGrid,function(model_,res_){
            ret = res_
            tmpGrid[i-1][j] = tmpGrid[i][j]
            tmpGrid[i][j] = tmp
            if(ret) stop = true
          })
        }
        if(i+1 < model_.gridSize && !ret){
          tmpGrid[i][j] = tmpGrid[i+1][j]
          tmpGrid[i+1][j] = tmp
          model_.detectCollapse(model_,tmpGrid,function(model_,res_){
            ret = res_
            tmpGrid[i+1][j] = tmpGrid[i][j]
            tmpGrid[i][j] = tmp
            if(ret) stop = true
          })
        }
        if(i == model_.gridSize-1 && j == model_.gridSize-1){
           model_.loadingDone = true
           callback_(ret)
         }
         if(stop) break
      }
      if(stop){
        model_.loadingDone = true
        callback_(ret)
        break
      }
    }
  }
  revereGrid(model_,callback_){
    let tmpGrid = []
    let ret = model_.grid.slice()
    for(let i=0; i<model_.gridSize;i++) tmpGrid.push(model_.grid[i].slice())
    for(let i=0; i<model_.gridSize; i++){
      for(let j=0; j < model_.gridSize; j++){
        ret[i][j] = tmpGrid[j][i]
      }
    }
    callback_(model_,ret)
  }
  detectCollapse(model_,grid_,callback_){

    let hasBreak = false
    for(let i=0; i<model_.gridSize; i++){
      for(let j=0; j<model_.gridSize; j++){
        if(grid_[i][j] != 0){
          let cond1 = (j-1 >= 0 && j-2 >= 0 && grid_[i][j] == grid_[i][j-1] && grid_[i][j] == grid_[i][j-2])
          let cond2 = (j+1 < model_.gridSize && j+2 < model_.gridSize && grid_[i][j] == grid_[i][j+1] && grid_[i][j] == grid_[i][j+2])
          let cond3 = (i-1 >= 0 && i-2 >= 0 && grid_[i][j] == grid_[i-1][j] && grid_[i][j] == grid_[i-2][j])
          let cond4 = (i+1 < model_.gridSize && i+2 < model_.gridSize && grid_[i][j] == grid_[i+1][j] && grid_[i][j] == grid_[i+2][j])
          if(cond1 || cond2 || cond3 || cond4){
            if(callback_ != undefined) {
              callback_(model_,true)
            }
            hasBreak = true
            break
          }
        }
      }
      if(hasBreak) break
    }
    if(!hasBreak && callback_ != undefined){
      callback_(model_,false)
    }
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
    this.loadingDone = true
  }
  initViewGrid(gridModel_){
    this.loadingDone = false
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
    this.loadingDone = true
  }
  swapCandies(fromCandy_,toCandy_,view_,callback_,args_){
    let context = view_.context
    fromCandy_.destinationPos = toCandy_.currentPos.slice()
    toCandy_.destinationPos = fromCandy_.currentPos.slice()
    view_.allowClick = false
    view_.loadingDone = false
    var t
    function swap(){
      t = setTimeout(function(){
          context.clearRect(fromCandy_.currentPos[0], fromCandy_.currentPos[1], view_.candySize, view_.candySize)
          context.clearRect(toCandy_.currentPos[0], toCandy_.currentPos[1], view_.candySize, view_.candySize)
          fromCandy_.move()
          toCandy_.move()
          context.drawImage(fromCandy_.img,fromCandy_.currentPos[0],fromCandy_.currentPos[1])
          context.drawImage(toCandy_.img,toCandy_.currentPos[0],toCandy_.currentPos[1])
          if(fromCandy_.isMoving() || toCandy_.isMoving())swap()
          else{
            view_.selectedCandy = undefined
            view_.hasEltSelected = false
            view_.loadingDone = true
            clearTimeout(t)
            if(callback_ != undefined) callback_(args_)
          }
        },5)
    }
    swap()
  }
  collapseCandies(listCandies_,view_,callback_,args_){
    var t
    view_.allowClick = false
    let context = view_.context
    view_.loadingDone = false
    function collapse(from_){
      t = setTimeout(function(){
        context.fillStyle = "lightblue";
        for(let i=0; i<listCandies_.length; i++){
          context.fillRect(listCandies_[i].currentPos[0]+from_,listCandies_[i].currentPos[1],1,view_.candySize)
        }
        if(from_+1 <= view_.candySize+1) collapse(from_+1)
        else{
          view_.allowClick = true
          for(let i=0; i<listCandies_.length; i++) listCandies_[i].isCollapse = true
          view_.loadingDone = true
          clearTimeout(t)
          if(callback_ != undefined) callback_(args_)
        }
      },5)
    }
    collapse(-1)
  }
  fallCandies(listCandies_,view_,callback_,args_){
    var t
    view_.allowClick = false
    view_.loadingDone = false
    let context = view_.context
    function fall(from_){
      t = setTimeout(function(){
        let areMoving = false
        for(let i=0; i<listCandies_.length; i++){
          context.clearRect(listCandies_[i].currentPos[0], listCandies_[i].currentPos[1], view_.candySize, view_.candySize)
          listCandies_[i].move(Utility.randInt(1,5))
          context.drawImage(listCandies_[i].img,listCandies_[i].currentPos[0],listCandies_[i].currentPos[1])
          if(listCandies_[i].isMoving()) areMoving = true
        }
        if(areMoving) fall()
        else{
          view_.allowClick = true
          view_.loadingDone = true
          clearTimeout(t)
          if(callback_ != undefined) callback_(args_)
        }
      },1)
    }
    fall()
  }
}

class Controller{
  constructor(){
    this.gridSize = 10
    this.candySize = 88
    this.model = undefined
    this.view = undefined
    this.currentCollapseGrid = []
    this.currentFallGrid = []
    this.currentRefillGrid = []
    this.currentIndex = -1
    this.points = document.getElementById("points")
  }
  start(){
    this.model = new Model(this.gridSize)
    this.view = new View(this.candySize,"draw")
    let t
    this.model.initGrid()
    function waitForModel(control_,model_){
      t = setTimeout(function(){
        if(!model_.loadingDone) waitForModel(control_,model_)
        else{
           clearTimeout(t)
           control_.view.initViewGrid(model_.traceGrid[0][0])
           let p
           function waitForView(control_,view_){
             p = setTimeout(function(){
               if(!view_.loadingDone) waitForView(control_,view_)
               else{
                  clearTimeout(p)
                  control_.collapse(control_)
                  control_.handleSwap(control_)
                }
             },10)
           }
           waitForView(control_,control_.view)
         }
      },10)
    }
    waitForModel(this,this.model)
  }
  handleSwap($this){
    $this.view.canvas.onclick = function(event){
      if($this.view.allowClick){
        $this.view.allowClick = false
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
                  $this.view.allowClick = true
                }
                else{
                  $this.checkSwap(xCandy,yCandy,$this.view.selectedCandy[0].currentPos[0],$this.view.selectedCandy[0].currentPos[1],$this,function(res){
                    if(res){
                      $this.view.viewGrid[i][j] = $this.view.selectedCandy[0]
                      $this.view.viewGrid[$this.view.selectedCandy[1]][$this.view.selectedCandy[2]] = candy
                      $this.view.swapCandies($this.view.selectedCandy[0],candy,$this.view,$this.collapse,$this)
                      $this.view.context.clearRect($this.view.selectedCandy[0].currentPos[0], $this.view.selectedCandy[0].currentPos[1], $this.view.candySize, $this.view.candySize)
                      $this.view.selectedCandy = undefined
                      $this.view.hasEltSelected = false
                    }
                    else{
                      $this.model.resetTraceGrid($this.model)
                      $this.view.context.clearRect($this.view.selectedCandy[0].currentPos[0], $this.view.selectedCandy[0].currentPos[1], $this.view.candySize, $this.view.candySize)
                      $this.view.context.drawImage($this.view.selectedCandy[0].img,$this.view.selectedCandy[0].currentPos[0],$this.view.selectedCandy[0].currentPos[1])
                      $this.view.context.fillStyle = "red";
                      $this.view.context.fillRect(candy.currentPos[0],candy.currentPos[1],$this.candySize,$this.candySize)
                      $this.view.context.drawImage(candy.img,i*$this.candySize,j*$this.candySize)
                      setTimeout(function(){
                         $this.view.context.clearRect(xCandy, yCandy, $this.candySize, $this.candySize)
                         $this.view.context.drawImage(candy.img,i*$this.candySize,j*$this.candySize)
                         $this.view.selectedCandy = undefined
                         $this.view.hasEltSelected = false
                         $this.view.allowClick = true
                       },50)
                     }
                  })
                }
              }
              else{
                $this.view.context.clearRect(xCandy, yCandy, $this.candySize, $this.candySize)
                $this.view.context.fillStyle = "lightgray";
                $this.view.context.fillRect(candy.currentPos[0],candy.currentPos[1],$this.candySize,$this.candySize)
                $this.view.context.drawImage(candy.img,i*$this.candySize,j*$this.candySize)
                $this.view.hasEltSelected = true
                $this.view.selectedCandy = [candy,i,j]
                $this.view.allowClick = true
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
  drawGrid(control_,callback_){

    control_.view.context.clearRect(0,0,control_.gridSize * control_.candySize,control_.gridSize * control_.candySize)
    for(let i=0; i<control_.view.viewGrid.length; i++){
      for(let j=0; j<control_.view.viewGrid[i].length; j++){
        if(control_.view.viewGrid[i][j].isCollapse || control_.view.viewGrid[i][j].id != control_.model.grid[i][j]){
          console.log("fix bug")
          let newCandy = new Candy(control_.model.grid[i][j])
          let img = new Image()
          img.src = newCandy.imgPath
          newCandy.img = img
          control_.view.viewGrid[i][j] = newCandy
         }
        control_.view.context.drawImage(control_.view.viewGrid[i][j].img,i*control_.candySize,j*control_.candySize)
      }
    }
    if(callback_ != undefined) callback_(control_)
  }
  collapse(control_){
    control_.currentIndex = control_.currentIndex + 1
    if(control_.model.traceGrid[1][control_.currentIndex] != undefined) control_.currentCollapseGrid = control_.model.traceGrid[1][control_.currentIndex].slice()
    else control_.currentCollapseGrid = []
    if(control_.model.traceGrid[2][control_.currentIndex] != undefined) control_.currentFallGrid = control_.model.traceGrid[2][control_.currentIndex].slice()
    else control_.currentFallGrid = []
    if(control_.model.traceGrid[3][control_.currentIndex] != undefined) control_.currentRefillGrid = control_.model.traceGrid[3][control_.currentIndex].slice()
    else control_.currentRefillGrid = []
    if(control_.currentCollapseGrid.length > 0){
      let listCandies = []
      for(let i=0; i<control_.currentCollapseGrid.length; i++){
        for(let j=0; j<control_.currentCollapseGrid[i].length; j++){
          if(control_.currentCollapseGrid[i][j] == 0) listCandies.push(control_.view.viewGrid[i][j])
        }
      }
      if(control_.currentFallGrid.length > 0){
         control_.view.collapseCandies(listCandies,control_.view,control_.fall,control_)
       }
      else {
        control_.view.collapseCandies(listCandies,control_.view,control_.refill,control_)
      }
    }
    else{
      var t
      function waitForLoading(control_){
        t = setTimeout(function(){
          if(control_.model.loadingDone && control_.view.loadingDone){
            control_.currentIndex = -1
            control_.model.resetTraceGrid(control_.model)
            control_.view.allowClick = true
            control_.updatePoints(control_)
            control_.drawGrid(control_,function(control_){
              control_.model.gameOver(control_.model,function(res){
                if(!res){
                   alert("GAMEOVER")
                   control_.start()
                 }
              })
            })
            clearTimeout(t)
          }
          else{
            waitForLoading(control_)
          }
        },5)
      }
      waitForLoading(control_)
     }
  }
  fall(control_){
    let listCandies = []
    for(let i=0; i<control_.currentFallGrid.length; i++){
      let currentCandy = control_.view.viewGrid[control_.currentFallGrid[i][0]][control_.currentFallGrid[i][1]]
      currentCandy.isCollapse = true
      let destX = control_.view.viewGrid[control_.currentFallGrid[i][2]][control_.currentFallGrid[i][3]].currentPos[0]
      let destY = control_.view.viewGrid[control_.currentFallGrid[i][2]][control_.currentFallGrid[i][3]].currentPos[1]
      control_.view.viewGrid[control_.currentFallGrid[i][2]][control_.currentFallGrid[i][3]] = new Candy(currentCandy.id)
      let destinationCandy = control_.view.viewGrid[control_.currentFallGrid[i][2]][control_.currentFallGrid[i][3]]
      destinationCandy.setCurrentPos(currentCandy.currentPos[0],currentCandy.currentPos[1])
      destinationCandy.setDestinationPos(destX,destY)
      let img = new Image()
      img.src = destinationCandy.imgPath
      destinationCandy.img = img
      listCandies.push(destinationCandy)
    }
    control_.view.fallCandies(listCandies,control_.view,control_.refill,control_)
  }
  refill(control_){
    let listCandies = []
    for(let i=0; i< control_.currentRefillGrid.length; i++){
      let x = control_.currentRefillGrid[i][0]
      let y = control_.currentRefillGrid[i][1]
      let newCandy = new Candy(control_.currentRefillGrid[i][2])
      control_.view.viewGrid[x][y] = newCandy
      let img = new Image()
      img.src = newCandy.imgPath
      newCandy.img = img
      newCandy.setCurrentPos(x * control_.candySize, -control_.candySize)
      newCandy.setDestinationPos(x * control_.candySize, y * control_.candySize)
      listCandies.push(newCandy)
    }
    control_.view.fallCandies(listCandies,control_.view,control_.collapse,control_)
  }
  updatePoints(control_){
    control_.points.innerHTML = "Points : "+control_.model.points
  }
}

var controller = new Controller()
controller.start()
