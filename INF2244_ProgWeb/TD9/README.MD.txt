Importer le fichier JSON : mongoimport --db charles --collection Restaurants --file base.json.

Interface utilisée : MongoDBCompass.

Vérification de création de la base : > mongo
				      > use charles
				      > show collections //Affichage de Restaurants


Il y a 25359 documents dans la collection : db.Restaurants.count().
Il y a 10259 restaurants à Manhattan : db.Restaurants.find({borough: "Manhattan"}).count()
Il y 339 pizzerias à Manhattan (db.Restaurants.find({borough: "Manhattan", cuisine: "Pizza" }).count()) et il y a en plus 119 restaurants
qui servent des pizzas (db.Restaurants.find({borough: "Manhattan", cuisine: {$regex: ".*Pizza.*"} }).count()), donc au total 458.
Il y a 1 restaurant français dans le Bronx : db.Restaurants.find({borough: "Bronx", cuisine: "French" }).count()
Il y a 327 pizzerias à Manhattan qui ont déjà été évalués en catégorie "A" : db.Restaurants.find({borough: "Manhattan", cuisine: "Pizza", "grades.grade": "A" }).count()

En triant avec un aggregate sur le nom et le quartier, on passe à 21327 restaurants.

Afin de tester l'appli web, il faut modifier les paramètres pour se connecter à la base mongodb (voir dans app.js).