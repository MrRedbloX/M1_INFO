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

def convolution(img, filtre,N=1,useType=False):
    ret = np.array(img)
    if useType : 
        ret = ret.astype(np.int8)
    pad = np.zeros((img.shape[0] + 2, img.shape[1] + 2))
    pad[1:-1, 1:-1] = img
    for i in range(img.shape[1]):
        for j in range(img.shape[0]):
            ret[j, i] = ((filtre * pad[j:j + 3, i:i + 3]).sum())/N
    return ret

Xl= np.fft.fft2(lenagrey)
Xls = np.fft.fftshift(Xl)
mXl = 20*np.log(np.abs(Xls))
pXl=np.angle(Xls)

Xc= np.fft.fft2(chimpanze)
Xcs = np.fft.fftshift(Xc)
mXc = 20*np.log(np.abs(Xcs))
pXc =np.angle(Xcs)
 
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
io.imshow(lenagreyInverse.real,cmap='gray')
plt.figure(6)
io.imshow(lenagreyInverseShift.real,cmap='gray')

plt.figure(7)
io.imshow(im11,cmap='gray')
plt.figure(8)
io.imshow(im22,cmap='gray')

filtre = [1,3,1,3,5,3,1,3,1]
io.imshow(convolve(chimpanze,filtre),cmap='gray')


