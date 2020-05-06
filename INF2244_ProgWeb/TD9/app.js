const express = require('express')
const app = express()
const {MongoClient} = require('mongodb');
const router = express.Router()
const port = process.env.PORT || 5000
const pug = require('pug');

//PARAMETRES A MODIFIER
const db = "charles"
const collection = "Restaurants"
const uri = "mongodb://localhost"

async function query(uri,db_name,collection_name,aggregate,callback){
  const client = new MongoClient(uri,{useUnifiedTopology: true})
  try {
      await client.connect()
      ret = await client.db(db_name).collection(collection_name).aggregate(aggregate).toArray()
  } catch (e) {
      console.error(e)
  } finally {
    await client.close()
    callback(ret)
  }
}

async function getTotalRestaurants(uri,db_name,collection_name,callback){
  const client = new MongoClient(uri,{useUnifiedTopology: true})
  try {
      await client.connect()
      ret = await client.db(db_name).collection(collection_name).countDocuments()

  } catch (e) {
      console.error(e)
  } finally {
    await client.close()
    callback(ret)
  }
}

router.get('/', (req,res) =>{
  getTotalRestaurants(uri,db,collection,(ret) =>{
    res.render("index", {total: ret+" restaurants available."})
  })
})

router.get('/noms/*', (req,res) =>{
  arr = req.originalUrl.split('/')
  cond = undefined
  if(arr.length == 3) cond = {cuisine: arr[2]}
  else if(arr.length == 4) cond = {cuisine: arr[3], borough: arr[2]}
  else res.render("search", {data: []})
  aggregate =[
    {
      $match: cond
    },
    {
      $group: {
          _id: {
            name: "$name", borough: "$borough"
          },
          addresses: {
            "$addToSet": "$address"
          }
      }
    },
    {
      $sort: {"_id.name": 1}
    },
  ]
  query(uri,db,collection,aggregate,(ret) =>{
    res.render("search", {data: JSON.stringify(ret)})
  })
})

router.get('/distance', (req,res) =>{
  x = req.query.x
  y = req.query.y
  query(uri,db,collection,undefined,(ret) =>{
    nearestRestaurant = ret[0]
    nearestDistance = Math.hypot(ret[0].address.coord[0]-x,ret[0].address.coord[1]-y)
    ret.forEach(elt => {
      newDistance = Math.hypot(elt.address.coord[0]-x,elt.address.coord[1]-y)
      if(newDistance < nearestDistance){
        nearestDistance = newDistance
        nearestRestaurant = elt
      }
    })
    res.render("distance", {x: x, y: y, restaurant: JSON.stringify(nearestRestaurant)})
  })
})

app.use('/', router)
app.set('view engine', 'pug')

app.listen(port, () => console.log("Express server listening on port "+port))
