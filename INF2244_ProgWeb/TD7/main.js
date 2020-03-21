const fs = require('fs')
const request = require('request')
const jsdom = require('jsdom')
const { JSDOM } = jsdom
const url = require('url')
const parse = require('url-parse')
const path = require('path')
const dir = "./images/"

function chargement(url,nomFichier,errCb){
  request.get({url : url, encoding : 'binary'}, (err,res,data) => {
    if(err == null && res.statusCode == 200){
      fs.writeFile(dir+nomFichier, data, 'binary', (err) =>{
        if(err != null) errCb(err,nomFichier)
      })
    }
  })
}

function recupereImages(url,prefixe,errCb){
  var hostname = parse(url).hostname
  hostname = "http://"+hostname //https sites also work
  request(url, (err,res,data) => {
    if(err == null && res.statusCode == 200){
      var dom = new JSDOM(data)
      dom.window.document.querySelectorAll("img").forEach((item, i) => {
        var parsed = parse(item.src)
        var extension = path.extname(path.basename(parsed.pathname))
        if(parsed.hostname == "") chargement(hostname+item.src,prefixe+i.toString()+extension,errCb)
        else chargement(item.src,prefixe+i.toString()+extension,errCb)
      });
      console.log("Done.")
    }
  })
}

recupereImages("https://www.pokerstars.fr","ps",(err,nomFichier) =>{
  console.log(nomFichier+" : "+err)
})
