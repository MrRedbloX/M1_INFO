var id = document.getElementById("plot")

function requestData(callback_){ // Requete sur la température et la pression
  $.get("/temp", function(data){
    var temp = JSON.parse(data)
    $.get("/pression", function(data){
      var press = JSON.parse(data)
      callback_(temp,press)
    })
  })
}

function createPlot(){ //Création du graphe
  requestData(function(temp,press){
    Plotly.newPlot(id, [ {
      x : temp.heure,
      y : temp.temp,
      mode: 'markers',
      type: 'scatter',
      name: 'Température'
    },
    {
      x : press.heure,
      y : press.pression,
      mode: 'lines',
      type: 'scatter',
      name: 'Pression'
    }
  ],{
    xaxis: {
      title: {
        text: 'Temps',
      },
    },
    yaxis: {
      title: {
        text: '°C / mbar',
      }
    }
  })
    Plotly.update(id,{
      'marker.color': 'blue'
    },{},0)
    Plotly.update(id,{
      'marker.color': 'yellow'
    },{},1)
    $("#date").text("Date : "+temp.quand)
  })
}

function updatePlot(){ //Mise à jour du graphe
  requestData(function(temp,press){
    Plotly.update(id, {
      x : [temp.heure],
      y : [temp.temp]
    },{},0)
    Plotly.update(id, {
      x : [press.heure],
      y : [press.pression]
    },{},1)
    $("#date").text("Date : "+temp.quand)
  })
}

function resizePlot(){ //Redimension du graphe
  Plotly.relayout(id,{
    autosize: true,
  })
}

$(function(){
  $("#news").load("/news")
  setInterval(function(){
    $("#news").load("/news") //Mise à jour des news toutes les 10 secondes
  },10000)
  $("#meteo").dialog({ //Fenetre dialog
    height: 600,
    width: 600,
    resize: resizePlot,
    position: { my: "center", at: "center", of: window }
  })
  createPlot()
  setInterval(updatePlot,4000) //Mise à jour du graphe toutes les 4 secondes
})
