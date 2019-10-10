# -*- coding: utf-8 -*-
"""
Created on Wed Apr 19 14:31:07 2017

@author: sylviegibet

"""

# Saisies 
import numpy as np
import matplotlib.pyplot as plt
from scipy.io.wavfile import read
from scipy.fftpack import fft
from scipy import signal

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

#fig2 = plt.figure()
#plt.xlabel('Frequency [Hz]')
#plt.ylabel('Phase')
#F1=F[100:256]
#pX1=pX[100:256]
#plt.plot(F1,pX1)

fig3 = plt.figure()
f, t, Sxx = signal.spectrogram(x, fs)
plt.pcolormesh(t, f, np.log(Sxx))
plt.ylabel('Frequency [Hz]')
plt.xlabel('Time [sec]')
