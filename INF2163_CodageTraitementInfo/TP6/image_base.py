#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Nov  4 17:04:55 2018

@author: sylviegibet et charlescogoluegnes
"""

from skimage import io
import matplotlib.pyplot as plt
import numpy as np


plt.figure(1)
lena_image = io.imread('lena.png')    #lecture d'une image
lenagrey = lena_image[:,:, 0] 
# ou bien :
#lenagrey = io.imread('./DATA/Images/lena.png', as_grey=True)
print("lena : ", lena_image.shape)      # taille de l'image
print("lena : ", lenagrey.shape)        # taille de l'image en noir et blanc
print(lena_image.dtype)
#print(lenagrey.dtype)                   # type de l'image
#io.imshow(lena_image)                   # affichage de l'image couleur
plt.figure(2)
#io.imshow(lena_image[:,:, 0])           # affichage de l'image en noir et blanc
#io.imshow(lenagrey)

# conversion du type de l'image : on passe de utf8 à float64
# en flottant, chaque pixel est normalisé entre 0 et 1
from skimage import img_as_float
lena = img_as_float(lenagrey)
print(lena.dtype)

# zoom de l'image
def zoom(img,factor):
    return img[150*factor:400*factor,150*factor:400*factor]

zoomLena = zoom(lenagrey,1)
io.imshow(zoomLena)


# sauvegarde de l'image zoomée
io.imsave("./lenagreayZoom.png",zoomLena)

def mask(img,x1,x2,y1,y2):
    ret = np.array(img)
    ret[:x1] = 255
    ret[:x2,:y1] = 255
    ret[x2:] = 255
    ret[:,y2:] = 255
    return ret

plt.figure(3)
io.imshow( mask(lenagrey,225,300,200,400))
plt.figure(4)
io.imshow(lenagrey)

plt.figure(5)
def resolution(img,scale):
    ret = np.arange(np.shape(img)[0]/2,np.shape(img)[1]/2)
    ret = img[::scale,::scale]
    return ret;
io.imshow(resolution(lenagrey,4))
    

