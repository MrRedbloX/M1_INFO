#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Nov  4 17:04:55 2018

@author: sylviegibet et charlescogoluegnes
"""

from skimage import io
import matplotlib.pyplot as plt
import numpy as np
from scipy.signal import convolve
from skimage.util import random_noise
from scipy.ndimage import median_filter
from skimage.morphology import binary_dilation
from skimage.measure import find_contours
from skimage.measure import regionprops

plt.figure(1)
lena_image = io.imread('lena.png')    #lecture d'une image
lenagrey = lena_image[:,:, 0]
print("lena : ", lena_image.shape)      # taille de l'image
print("lena : ", lenagrey.shape)        # taille de l'image en noir et blanc
print(lena_image.dtype)
chimpanze = io.imread('chimpanze.jpg',as_grey=True)
#print(lenagrey.dtype)                   # type de l'image
#io.imshow(lena_image)                   # affichage de l'image couleur
plt.figure(2)
#io.imshow(lena_image[:,:, 0])     
plt.title("Image de base")      # affichage de l'image en noir et blanc
io.imshow(lenagrey)

# conversion du type de l'image : on passe de utf8 à float64
# en flottant, chaque pixel est normalisé entre 0 et 1
from skimage import img_as_float
lena = img_as_float(lenagrey)
print(lena.dtype)

#Fourrier
Xl= np.fft.fft2(lenagrey)
Xls = np.fft.fftshift(Xl)
mXl = 20*np.log(np.abs(Xls))
pXl=np.angle(Xls)

Xc= np.fft.fft2(chimpanze)
Xcs = np.fft.fftshift(Xc)
mXc = 20*np.log(np.abs(Xcs))
pXc =np.angle(Xcs)

#Recomposition de l'image
F11 = np.abs(Xcs)*np.exp(pXl*1j); 
F22= np.abs(Xls)*np.exp(pXc*1j); 
im11= np.abs(np.fft.ifft2(np.fft.ifftshift(F11))); 
im22= np.abs(np.fft.ifft2(np.fft.ifftshift(F22)));

plt.figure(3)
plt.title("Amplitude")
io.imshow(mXl,cmap='gray');
plt.figure(4)
plt.title("Phase")
io.imshow(pXl,cmap='gray')

lenagreyInverse = np.abs(np.fft.ifft2(Xl))
lenagreyInverseShift = np.fft.ifftshift(lenagreyInverse)
plt.figure(5)
plt.title("Lena recomposée")
io.imshow(lenagreyInverse.real,cmap='gray')
plt.figure(6)
plt.title("Lena recomposée shift")
io.imshow(lenagreyInverseShift.real,cmap='gray')

plt.figure(7)
plt.title("Phase lena amplitude chimpanze")
io.imshow(im11,cmap='gray')
plt.figure(8)
plt.title("Phase chimpanze amplitude lena")
io.imshow(im22,cmap='gray')

filtrePasseBas = [1,3,1,3,5,3,1,3,1]
filtrePasseBas = np.reshape(filtrePasseBas,(3,3))
plt.figure(9)
plt.title("Filtre passe-bas")
io.imshow(convolve(lenagrey,filtrePasseBas),cmap='gray')

filtrePasseHaut = [0,-1,0,-1,5,-1,0,-1,0]
filtrePasseHaut = np.reshape(filtrePasseHaut,(3,3))
plt.figure(10)
plt.title("Filtre passe-haut")
io.imshow(convolve(lenagrey,filtrePasseHaut),cmap='gray')

plt.figure(11)
plt.title("Filtre passe-bas Fourrier")
io.imshow(np.abs(np.fft.ifft2(np.fft.ifftshift(convolve(Xl,filtrePasseBas)))),cmap='gray')

plt.figure(12)
plt.title("Filtre passe-haut Fourrier")
io.imshow(np.abs(np.fft.ifft2(np.fft.ifftshift(convolve(Xl,filtrePasseHaut)))),cmap='gray')

lenaNoise = random_noise(lenagrey,mode='s&p',amount=0.5)
plt.figure(13)
plt.title("Salt And Pepper 0.5")
io.imshow(lenaNoise, cmap='gray')

plt.figure(14)
plt.title("Filtre médiant sur le bruit")
io.imshow(median_filter(lenaNoise,size=8,mode='constant',cval=0.5), cmap='gray')    

def seuillage(img,N):
    for i in range(0,np.shape(img)[0]):
        for j in range(0,np.shape(img)[1]):
            if img[i,j] < N:
                img[i,j] = 255
            else :
                img[i,j] = 0 

forme = io.imread('jetons.jpg')  
plt.figure(15)
plt.title("Formes")
io.imshow(forme, cmap='gray')   

N = 200
seuillage(forme[:,:,0],N)
seuillage(forme[:,:,1],N)
seuillage(forme[:,:,2],N)
forme = forme[:,:,2]
forme = convolve(forme,filtrePasseBas)
plt.figure(16)
plt.title("Formes seuillage 200 + filtre passe-bas")
io.imshow(forme, cmap='gray')

print("Nombre de formes détecté : ",len(find_contours(forme,0.8)))

