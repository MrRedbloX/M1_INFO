#%%
#A compiler en premier
import numpy as np
from scipy.fftpack import fft
import matplotlib.pyplot as plt

#%%
def genSin(N,f,fs):
    t = np.linspace(0,(N-1)/fs, N)
    pi = np.pi
    x = np.sin(2*pi*f*t)
    return x

def genSig(N,fs):
    t = np.linspace(0,(N-1)/fs, N)
    pi = np.pi
    x = 3*np.cos(50*pi*t) + 10*np.sin(300*pi*t) - np.sin(100*pi*t);
    return x

fe = 300
N = 512
T = float(N-1)/fe
echantillons = np.zeros(N)
#echantillons = genSin(N,1,fe)+0.5*genSin(N,2,fe)+0.25*genSin(N,3,fe)+0.01*genSin(N,4,fe)
#echantillons = genSin(N,16,fe) + genSin(N,10,fe)
#echantillons = genSin(N,128,fe)
echantillons = genSig(N,fe) 
n = np.arange(N)
plt.plot(n[0:50], echantillons[0:50])

TFD = fft(echantillons)
Re = np.real(TFD)
Im = np.imag(TFD)

freq = np.linspace(0,fe,N)
plt.figure(figsize=(10,4))
plt.plot(freq,Re,'g')
plt.xlabel('fréquence')
plt.ylabel('Réelle')	
plt.axis([0,fe/2,0,Re.max()])	
plt.grid()	

plt.figure(figsize=(10,4))	
plt.plot(freq,Im,'g')	
plt.xlabel('fréquence')	
plt.ylabel('Imaginaire')	
plt.axis([0,fe/2,0,Im.max()])	
plt.grid()

A = np.absolute(TFD/N)
An = A/A.max()
P = np.angle(TFD/N)

plt.figure(figsize=(10,4))	
plt.plot(freq,An,'b')	
plt.xlabel('fréquence')	
plt.ylabel('Amplitude')	
plt.axis([0,fe/2,0,An.max()])	
plt.grid()

plt.figure(figsize=(10,4))	
plt.plot(freq,P,'r')	
plt.xlabel('fréquence')	
plt.ylabel('Phase')	
plt.axis([0,fe/2,0,P.max()])	
plt.grid()

F = np.linspace(0, fe, N)
plt.figure(figsize=(10,4))	
plt.plot(F, abs(TFD))	
plt.xlabel('fréquence')	
plt.ylabel('Spectre')	
plt.axis([0,fe/2,0,F.max()])	
plt.grid()