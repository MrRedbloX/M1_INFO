const express = require('express')
const app = express()
const path = require('path')
const router = express.Router()
const port = process.env.PORT || 5000
const levenshtein = require('js-levenshtein')
const url = require('url')
const pug = require('pug');

var cles = {
  "a1234" : {
    "nbReq" : 0,
    "isLock" : false
  },
  "b1234" : {
    "nbReq" : 0,
    "isLock" : false
  },
  "c1234" : {
    "nbReq" : 0,
    "isLock" : false
  },
  "d1234" : {
    "nbReq" : 0,
    "isLock" : false
  },
  "e1234" : {
    "nbReq" : 0,
    "isLock" : false
  },
  "f1234" : {
    "nbReq" : 0,
    "isLock" : false
  },
  "g1234" : {
    "nbReq" : 0,
    "isLock" : false
  },
  "h1234" : {
    "nbReq" : 0,
    "isLock" : false
  },
  "i1234" : {
    "nbReq" : 0,
    "isLock" : false
  },
  "j1234" : {
    "nbReq" : 0,
    "isLock" : false
  }
}

router.get('*', (req,res) =>{
  let ulp = url.parse(req.url)
  if(/^\/[a-zA-Z][0-9][0-9][0-9][0-9]\/distance$/.test(ulp.pathname)){
    let cle = ulp.pathname.split("/")[1]
    if(cle in cles){
      let a = req.query.a
      let b = req.query.b
      if(a == undefined || b == undefined) res.send({
        "utilisateur" : cle,
        "erreur" : "la requête est mal formée"
      })
      else if(a.length > 50 || b.length > 50) res.send({
        "utilisateur" : cle,
        "erreur" : "une des deux chaînes est trop longue (gardez des chaînes inférieures à 50)"
      })
      else if(!(/^[A|C|G|T][A|C|G|T]*$/.test(a)) || !(/^[A|C|G|T][A|C|G|T]*$/.test(b))) res.send({
        "utilisateur" : cle,
        "erreur" : "une des chaînes ne code pas de l’ADN"
      })
      else{
        cles[cle].nbReq++
        if(cles[cle].isLock == false){
          if(cles[cle].nbReq > 5){
            cles[cle].isLock = true
            setTimeout(()=>{
              cles[cle].isLock = false
              cles[cle].nbReq = 0
            }, 60000)
            res.send({
              "utilisateur" : cle,
              "erreur" : "nombre de requêtes dépassé, attendez une minute"
            })
          }
          else{
            let today = new Date()
            let date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate()
            let time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()
            let dateTime = date+' '+time
            let start = process.hrtime()[1]
            let distance = levenshtein(a,b)
            let end = process.hrtime()[1] - start
            res.send({
              "utilisateur" : cle,
              "date" : dateTime,
              "A" : a,
              "B" : b,
              "distance" : distance,
              "tempsDeCalculEnMs" : end/1000000,
              "interrogationsMinute" : cles[cle].nbReq
            })
          }
        }
        else res.send({
          "utilisateur" : cle,
          "erreur" : "nombre de requêtes dépassé, attendez une minute"
        })
      }
    }
    else res.send({
      "utilisateur" : cle,
      "erreur" : "vous n’avez pas les autorisations pour utiliser ce service"
    })
  }
  else if(ulp.pathname == "/form") res.render('form')
  else res.render('index')
})

app.use('/', router)
app.set('view engine', 'pug')

app.listen(port, () => console.log("Express server listening on port "+port))
