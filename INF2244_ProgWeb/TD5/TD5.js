$(document).ready(function(){
  var enableClicks = true
  var selectedId = $("#img3").attr("id")
  $(".panneauTxt").hide()
  $(".panneauTxt").attr("display","block")
  $(".volet").click(function(){
    if(enableClicks){
      let id = $(this).attr("id")
      let left = true
      enableClicks = false
      $(".volet").each(function() {
        let classe = $(this).attr("class").split(" ")[0]
        let c_id = $(this).attr("id")
        if(id == c_id  && classe != "central"){
          selectedId = id
           $(this).animate({
             width : "50%"
           }, "slow",function(){
             $(this).attr("class","central volet")
             left = false
             $(this).children(".txtbulle").show("fast")
             enableClicks = true
           })
         }
        if(classe == "central" && id != c_id){
          $(this).animate({
            width : "12.5%"
          }, "slow", function(){
            if(left) $(this).attr("class","imgG volet")
            else $(this).attr("class","imgD volet")
            enableClicks = true
          })
        }
        if(id == c_id && classe == "central"){
          enableClicks = true
        }
      })
    }
  })
  $(".volet").hover(function(){
    if($(this).attr("id") == selectedId) $(this).children(".txtbulle").show("fast")
  }, function(){
    $(this).children(".txtbulle").hide("fast")
  })
})
