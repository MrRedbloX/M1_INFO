$("#button").click(function(){
  let value  = $.trim($("#montexte").val())
  //alert(value);
  eval(value)
})
//document.write("coucou")
$("#lettre_jaune").data({
    'originalLeft': $("#lettre_jaune").css('left'),
    'origionalTop': $("#lettre_jaune").css('top')
});
$("#lettre_rouge").data({
    'originalLeft': $("#lettre_rouge").css('left'),
    'origionalTop': $("#lettre_rouge").css('top')
});
$("#lettre_bleu").data({
    'originalLeft': $("#lettre_bleu").css('left'),
    'origionalTop': $("#lettre_bleu").css('top')
});
function reset(id){
  $(id).css({
    'left': $(id).data('originalLeft'),
    'top': $(id).data('origionalTop')
});
}
$(function() {
  $("#lettre_jaune").draggable()
  $("#lettre_rouge").draggable()
  $("#lettre_bleu").draggable()

  $("#boite_jaune").droppable({
    drop : function(event,ui){
      if(event.originalEvent.target.id === "lettre_jaune"){
        $("#boite_jaune").attr('src',"./TDJQuery1/boite_jaune_ouverte.jpg")
        let inter =  setInterval(function () {
          $("#boite_jaune").attr('src',"./TDJQuery1/boite_jaune_fermee.jpg")
          clearInterval(inter)
        }, 100);
         $("#montexte").text("Boite Jaune recoit une lettre jaune")
         reset("#lettre_jaune")
      }
      else if(event.originalEvent.target.id === "lettre_rouge") reset("#lettre_rouge")
      else if(event.originalEvent.target.id === "lettre_bleu") reset("#lettre_bleu")
    }
  })
  $("#boite_rouge").droppable({
    drop : function(event,ui){
      if(event.originalEvent.target.id === "lettre_rouge"){
        $("#boite_rouge").attr('src',"./TDJQuery1/boite_rouge_ouverte.jpg")
        let inter =  setInterval(function () {
          $("#boite_rouge").attr('src',"./TDJQuery1/boite_rouge_fermee.jpg")
          clearInterval(inter)
        }, 100);
         $("#montexte").text("Boite Rouge recoit une lettre rouge")
         reset("#lettre_rouge")
      }
      else if(event.originalEvent.target.id === "lettre_jaune") reset("#lettre_jaune")
      else if(event.originalEvent.target.id === "lettre_bleu") reset("#lettre_bleu")
    }
  })
  $("#boite_bleu").droppable({
    drop : function(event,ui){
      if(event.originalEvent.target.id === "lettre_bleu"){
        $("#boite_bleu").attr('src',"./TDJQuery1/boite_bleu_ouverte.jpg")
        let inter =  setInterval(function () {
          $("#boite_bleu").attr('src',"./TDJQuery1/boite_bleu_fermee.jpg")
          clearInterval(inter)
        }, 100);
         $("#montexte").text("Boite Bleu recoit une lettre bleu")
         reset("#lettre_bleu")
      }
      else if(event.originalEvent.target.id === "lettre_rouge") reset("#lettre_rouge")
      else if(event.originalEvent.target.id === "lettre_jaune") reset("#lettre_jaune")
    }
  })
});
