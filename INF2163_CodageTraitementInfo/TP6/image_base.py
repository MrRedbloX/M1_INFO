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
from scipy.fftpack import fft2
from scipy import signal

plt.figure(1)
lena_image = io.imread('lena.png')    #lecture d'une image
lenagrey = lena_image[:,:, 0] 
# ou bien :
chimpgrey = io.imread('chimpanze.jpg', as_grey=True)
print("lena : ", lena_image.shape)      # taille de l'image
print("lena : ", lenagrey.shape)        # taille de l'image en noir et blanc
print(lena_image.dtype)
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

# zoom de l'image
def zoom(img,factor):
    return img[150*factor:400*factor,150*factor:400*factor]
plt.figure(3)
plt.title("Zoom")  
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

plt.figure(4)
plt.title("Mask")  
io.imshow(mask(lenagrey,225,300,200,400))

plt.figure(5)
def echantillonage(img,scale):
    ret = np.arange(np.shape(img)[0]/scale,np.shape(img)[1]/scale)
    ret = img[::scale,::scale]
    return ret;
plt.title("Echantillonnage")  
io.imshow(echantillonage(lenagrey,6))

def normaliser(img,N):
    ret = np.array(img)
    ret[:,:] = ret[:,:]/N
    return ret
plt.figure(6)
plt.title("Normalisation 0.2")  
io.imshow(normaliser(lenagrey,0.2))
plt.figure(7)
plt.title("Normalisation 0.5")  
io.imshow(normaliser(lenagrey,0.5))
plt.figure(8)
plt.title("Normalisation 0.8")  
io.imshow(normaliser(lenagrey,0.8))

def histogramme(img,N):
    ret = np.zeros(256);  
    for i in range(0,np.shape(img)[0]-1):
        for j in range(0,np.shape(img)[1]-1):
            ret[img[i,j]] += 1
    ret[:] = ret[:]/N
    return ret
plt.figure(9)
plt.title("Histogramme")  
plt.stem(histogramme(lenagrey,14)) #14 est environ égal au max de pixel de l'histogramme divisé par 255

def histCumul(img,N):
    hist = histogramme(img,N)
    for i in range(0,np.size(hist)):
        if i != 0:
            hist[i] += hist[i-1]
    return hist
plt.figure(10)
plt.title("Histogramme cumulé")
plt.stem(histCumul(lenagrey,980))

def egalisation(img,N):
    ret = np.array(img)
    histCum = histCumul(ret,N)  #On se base sur l'histogramme cumulé normalisé pour assigner les nouvelles valeurs aux pixels
    for i in range(0,np.shape(ret)[0]):
        for j in range(0,np.shape(ret)[1]):
            ret[i,j] = histCum[img[i,j]]
    return ret
plt.figure(11)
plt.title("Dark Chimpanze")
darkChimp = normaliser(chimpgrey,5)
io.imshow(darkChimp)
plt.figure(12)
plt.title("Egalisation")
io.imshow(egalisation(darkChimp,255))

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

filtre = [-1,-1,-1,-1,8,-1,-1,-1,-1]
filtre = np.reshape(filtre,(3,3))
plt.figure(13)
plt.title("Convolution Detection de contours")
#io.imshow(convolve(lenagrey,filtre))
io.imshow(convolution(lenagrey,filtre,useType=True),cmap='gray')
filtre = [0,-1,0,-1,5,-1,0,-1,0]
filtre = np.reshape(filtre,(3,3))
plt.figure(14)
plt.title("Convolution Sharpen")
#io.imshow(convolve(lenagrey,filtre))
io.imshow(convolution(lenagrey,filtre,useType=True),cmap='gray')

gaussian = [1,2,1,2,4,2,1,2,1]
gaussian = np.reshape(gaussian,(3,3))
gaussian = gaussian*0.625
plt.figure(15)
plt.title("Convolution Gaussian")
#io.imshow(convolve(lenagrey,gaussian))
io.imshow(convolution(lenagrey,gaussian,useType=True),cmap='gray')

filtreMoy = [1,1,1,1,0,1,1,1,1]
filtreMoy = np.reshape(filtreMoy,(3,3))
plt.figure(16)
plt.title("Filtre moyenneur")
#io.imshow(convolve(lenagrey,filtreMoy))
io.imshow(convolution(lenagrey,filtreMoy,N=9,useType=True),cmap='gray')

X=fft2(lenagrey)
X = np.fft.fftshift(X)
mX = 20*np.log(np.abs(X))
pX=np.angle(X)

plt.figure(17)
plt.title("Amplitude")
io.imshow(mX,cmap='gray');
plt.figure(18)
plt.title("Phase")
io.imshow(pX,cmap='gray')

def createFilter(co,fe,order,btype):
    cutoff = co/(0.5*fe)
    return signal.butter(order,cutoff,btype=btype, analog=False)

def applyFilter(img,co,fe,order,btype):
    b,a = createFilter(co,fe,order,btype)
    return signal.lfilter(b,a,img)

co = 3
fe = 30
order = 10
pb = applyFilter(lenagrey,co,fe,order,"lowpass")
ph = applyFilter(lenagrey,co,fe,order,"highpass")

plt.figure(19)
plt.title("Filtre passe-bas")
io.imshow(pb,cmap='gray');
plt.figure(20)
plt.title("Filtre passe-haut")
io.imshow(ph,cmap='gray');



