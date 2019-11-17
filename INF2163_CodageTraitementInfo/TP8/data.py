# -*- coding: utf-8 -*-
"""
Created on Thu Nov 14 21:57:56 2019

@author: cogoluegnescharles
"""

import pandas as pd #pour l’exploration de données
import numpy as np #pour les opérations numériques
import matplotlib.pyplot as plt

unames = ['user_id', 'gender', 'age', 'occupation', 'zip']
users = pd.read_csv('ml-1m/users.dat', sep='::', header=None, names=unames, engine='python')
print(users.head()) #Affiche les 5 premières valeurs

uratings = ['user_id', 'movie_id', 'rating', 'timestamp']
ratings = pd.read_csv('ml-1m/ratings.dat', sep='::', header=None, names=uratings, engine='python')
print(ratings.head(10)) #Affiche les 10 premières valeurs

umovies = ['movie_id','title', 'genres']
movies = pd.read_csv('ml-1m/movies.dat', sep='::', header=None, names=umovies, engine='python')
print(movies.head(10))

data = pd.merge(pd.merge(users,ratings), movies)
print(data.head())

#%%
print("Nb films where note > 4.5 : ", np.sum(data.rating > 4.5))
print("Nb films where note > 4.5 and gender = 'F' : ", np.sum(data.rating[data.gender == 'F']> 4.5))
print("Nb films where note > 4.5 and gender = 'M' : ", np.sum(data.rating[data.gender == 'M']> 4.5))
res = np.sum(data.rating[data.gender == 'F']> 4.5)/np.sum(data.rating[data.gender == 'F'])*100
print("Proportions de films note > 4.5 par les femmes : ",res," %")
res = np.sum(data[(data.gender == 'M') & (data.age >= 30)].groupby('movie_id', axis=0)['rating'].median() >= 4.5)
print("Films ayant une note médiane > 4.5 parmis hommes dont age >= 30 : ", res)
res = np.sum(data[(data.gender == 'F') & (data.age >= 30)].groupby('movie_id', axis=0)['rating'].median() >= 4.5)
print("Films ayant une note médiane > 4.5 parmis femmes dont age >= 30 : ", res)
res = data.groupby('movie_id', axis=0)['rating'].mean().nlargest(15)
res = movies[movies.movie_id.isin(res)]
print("Films les plus populaires : ")
print(res)
print("Films suffisamment populaires : ")
print(data.groupby('movie_id', axis=0)['rating'].count().head())
data2 = pd.concat([data.groupby('movie_id', axis=0)['rating'].mean(), data.groupby('movie_id', axis=0)['rating'].count()], axis=1)
data2.columns = ['mean_rating', 'n_rating']
print(data2.head())
populariteMin = data2['mean_rating'].median()
print("Popularité minimum : ", populariteMin)
res = data2[data2.mean_rating > populariteMin]
res = res.sort_values('n_rating', ascending=False).head(2).index
print("Titre des 2 films les plus populaires selon la nouvelle méthode : ")
res = movies[movies.movie_id.isin(res)]
print(res['title'])
res = data2.sort_values('n_rating', ascending=False).head(1).index
print("Le film le plus souvent noté : ")
res = movies[movies.movie_id.isin(res)]
print(res['title'])

#%%
plt.figure(1)
plt.title("Histogramme des notes de tout les films")
print(data.rating.hist(bins=5, align='left', range=[1, 6]))
plt.figure(2)
plt.title("Histrogramme du nombre de notes reçues par film")
print(data.groupby('movie_id', axis=0)['rating'].count().hist(bins=10))
plt.figure(3)
plt.title("Histrogramme des notes moyennes des films")
print(data.groupby('movie_id', axis=0)['rating'].median().hist(bins=10))
map_id_to_count = data.groupby('movie_id')['rating'].count().to_dict()
plt.figure(4)
plt.title("Histrogramme des notes des films notés plus de 30 fois")
# rajoute une variable comptant le nombre de votes par film
data['movie_count'] = data['movie_id'].map(map_id_to_count)
# changer kde par hist pour retrouver un histogramme et non une estimation de la densité
data[data.movie_count >= 30].groupby('movie_id', axis=0)['rating'].mean().plot(kind='hist', color='b')
data[data.movie_count <= 30].groupby('movie_id', axis=0)['rating'].mean().plot(kind='hist', color='g')
#plt.figure(5)
#plt.title("Moyenne des notes des films notés plus 100 fois hommes(b) vs femmes(g)")
#axis = plt.gca()
res = data[(data.gender == 'M') & (data.movie_count >= 100)].groupby('movie_id', axis=0)['rating'].median().to_frame()
res.reset_index(inplace=True)
res.columns = ['movie_id','rating']
res = res.astype(float)
res.plot(title='Moyenne notes films notés plus de 100 fois par les hommes',kind='scatter', x='rating', y='movie_id', c='b')
res = data[(data.gender == 'F') & (data.movie_count >= 100)].groupby('movie_id', axis=0)['rating'].median().to_frame()
res.reset_index(inplace=True)
res.columns = ['movie_id','rating']
res = res.astype(float)
res.plot(title='Moyenne notes films notés plus de 100 fois par les femmes',kind='scatter', x='rating', y='movie_id', c='g')
#plt.figure(6)
#plt.title("Moyenne des notes des films notés moins 100 fois hommes(b) vs femmes(g)")
#axis = plt.gca()
res = data[(data.gender == 'M') & (data.movie_count < 100)].groupby('movie_id', axis=0)['rating'].median().to_frame()
res.reset_index(inplace=True)
res.columns = ['movie_id','rating']
res = res.astype(float)
res.plot(title='Moyenne notes films notés moins de 100 fois par les hommes',kind='scatter', x='rating', y='movie_id', c='b')
res = data[(data.gender == 'F') & (data.movie_count < 100)].groupby('movie_id', axis=0)['rating'].median().to_frame()
res.reset_index(inplace=True)
res.columns = ['movie_id','rating']
res = res.astype(float)
res.plot(title='Moyenne notes films notés moins de 100 fois par les femmes',kind='scatter', x='rating', y='movie_id', c='g')
print("On remarque que les femmes ont tendance à attribuer plus souvent une très bonne note au film que les hommes.")
print("Elles ont tendance aussi à attribuer moins de mauvaises notes que les hommes.")



