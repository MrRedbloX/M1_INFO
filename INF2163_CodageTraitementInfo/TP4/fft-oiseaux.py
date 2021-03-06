# -*- coding: utf-8 -*-
"""
Created on Wed Apr 19 14:31:07 2017

@author: sylviegibet

"""

# Saisies 
#%%
# A exécuter en premier
import numpy as np
import matplotlib.pyplot as plt
from scipy.io.wavfile import read
from scipy.fftpack import fft
from scipy import signal

#%%

#(fs,x) = read('./DATA/Sons/Oiseaux/etourneau.wav')
#(fs,x) = read('./DATA/Sons/Oiseaux/fauvette.wav')
(fs,x) = read('./DATA/Sons/Oiseaux/piebavarde.wav')
#(fs,x) = read('./DATA/Sons/Oiseaux/rossignol.wav')
#(fs,x) = read('./DATA/Sons/Classique/nocturne_chopin.wav')


print('taille du fichier : ', x.size)
print('fréquence d\'échantillonnage : ', fs)
print('durée du signal : ', x.size/fs, 's')

Nf=4096
offset=256   
sf = np.zeros(Nf)
sf[:]=x[offset:offset+Nf]
X=fft(sf)/Nf
F =np.linspace(0,fs,Nf)	
mX = 2*abs(X)
powerSpectra = 10 * np.log10(abs(X))
pX=np.angle(X)
print(np.size(mX))


fig1 = plt.figure()
plt.xlabel('Frequency [Hz]')
plt.ylabel('Magnitude')
F1=F[256:1024+256]
mX1=mX[256:1024+256]
#mX1=powerSpectra[100:256]
plt.plot(F1,mX1)

fig2 = plt.figure()
plt.xlabel('Frequency [Hz]')
plt.ylabel('Phase')
F1=F[100:256]
pX1=pX[100:256]
plt.plot(F1,pX1)

fig3 = plt.figure()
f, t, Sxx = signal.spectrogram(x, fs)
plt.pcolormesh(t, f, np.log(Sxx))
plt.ylabel('Frequency [Hz]')
plt.xlabel('Time [sec]')

#%%

def calculerSpectre(echantillons,fs, indB=False):
    Nf = echantillons.size
    F =np.linspace(0,fs,Nf)	
    X=fft(echantillons)/Nf
    if(indB):
        X = 10 * np.log10(abs(X))
    return (F,X)

def movingFFT(filename, winSize, offset, winType):
    (fs,x) = read(filename)
    Nf = x.size
    (F,X) = calculerSpectre(x,fs,True)
    plt.figure() 
    X = X*signal.get_window(winType,Nf)
    mX = 2*abs(X)
    plt.plot(F[offset : winSize+offset], mX[offset : winSize+offset])
    
movingFFT('./DATA/Sons/Oiseaux/piebavarde.wav', 100, 256, "hamming")
movingFFT('./DATA/Sons/Oiseaux/etourneau.wav', 1024, 2056, "hamming")
movingFFT('./DATA/Sons/Oiseaux/fauvette.wav', 256, 512, "hanning")
movingFFT('./DATA/Sons/Oiseaux/rossignol.wav', 2056, 5072, "hanning")
    




