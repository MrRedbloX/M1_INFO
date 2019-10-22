#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Oct 15 17:06:56 2018

@author: sylviegibet and charlescogoluegnes
"""

import numpy as np
from scipy import signal
import matplotlib.pyplot as plt
from scipy.io.wavfile import read
from scipy.io import wavfile

    
#######
# I) Filtre FIR
#######

fe = 8000

ordreFIR = 100

# Filtre 0-500 Hz
f1F = 0.01
f2F = 500
b1F = signal.firwin(ordreFIR+1, [f1F, f2F], pass_zero=False,window='hann', fs=fe)

# Filtre 500-2000 Hz
f1F = 500
f2F = 2000
b2F = signal.firwin(ordreFIR+1, [f1F, f2F], pass_zero=False,window='hann', fs=fe)

# Filtre 2000-4000 Hz
f1F = 2000
f2F = 3999
b3F = signal.firwin(ordreFIR+1, [f1F, f2F], pass_zero=False, window='hann', fs=fe)

#construction d'un filtre passe-bas, avec freq. de coupure 0.1 ;
#numtaps = 41 #nombre de coef du filtre (impair)
#cutoff: liste de fréquences de coupure (une seule ici) ; 
#Ces freq. sont relatives à la freq d'échantillonnage

#b1=signal.firwin(numtaps,cutoff=[0.1],window='hann',nyq=0.5)

# Tracer la réponse impulsionnelle :
plt.figure(figsize=(10,5))
plt.stem(b1F)
plt.title("Réponse impulsionnelle b1F")
plt.xlabel("n")
plt.xlabel("b")
plt.grid()

plt.figure(figsize=(10,5))
plt.stem(b2F)
plt.title("Réponse impulsionnelle b2F")
plt.xlabel("n")
plt.xlabel("b")
plt.grid()

plt.figure(figsize=(10,5))
plt.stem(b3F)
plt.title("Réponse impulsionnelle b3F")
plt.xlabel("n")
plt.xlabel("b")
plt.grid()


#Réponse fréquentielle
w,H=signal.freqz(b1F)
plt.figure(figsize=(10,5))
plt.title("Réponse fréquentielle b1F")
plt.plot(w/(2*np.pi),20*np.log10(np.absolute(H)))
plt.xlabel("f/fe")
plt.ylabel("Amplitude")
plt.grid()    
plt.figure(figsize=(10,5)) 
plt.title("Réponse fréquentielle b1F")
plt.plot(w/(2*np.pi),np.unwrap(np.angle(H)))
plt.xlabel("f/fe")
plt.ylabel("phase")
plt.grid()

w,H=signal.freqz(b2F)
plt.figure(figsize=(10,5))
plt.title("Réponse fréquentielle b2F")
plt.plot(w/(2*np.pi),20*np.log10(np.absolute(H)))
plt.xlabel("f/fe")
plt.ylabel("Amplitude")
plt.grid()    
plt.figure(figsize=(10,5)) 
plt.title("Réponse fréquentielle b2F")
plt.plot(w/(2*np.pi),np.unwrap(np.angle(H)))
plt.xlabel("f/fe")
plt.ylabel("phase")
plt.grid()

w,H=signal.freqz(b3F)
plt.figure(figsize=(10,5))
plt.title("Réponse fréquentielle b3F")
plt.plot(w/(2*np.pi),20*np.log10(np.absolute(H)))
plt.xlabel("f/fe")
plt.ylabel("Amplitude")
plt.grid()    
plt.figure(figsize=(10,5)) 
plt.title("Réponse fréquentielle b3F")
plt.plot(w/(2*np.pi),np.unwrap(np.angle(H)))
plt.xlabel("f/fe")
plt.ylabel("phase")
plt.grid()


#######
#II) Filtre IIR
#######

ordreIIR = 2

# Filtre 0-500 Hz
f2I = 500
b1aI, b1bI = signal.iirfilter(ordreIIR, f2I/fe, btype="lowpass", ftype="butter")

# Filtre 500-2000 Hz
f1I = 500
f2I = 2000
b2aI, b2bI = signal.iirfilter(ordreIIR, [f1I/fe*f2I/fe], btype="lowpass", ftype="butter")

# Filtre 2000-4000 Hz
f1I = 2000
f2I = 4000
b3aI, b3bI = signal.iirfilter(ordreIIR, [f1I/fe*f2I/fe], btype="lowpass", ftype="butter")

#b3,a3 = signal.iirfilter(N=2,Wn=[0.1*2],btype="lowpass",ftype="butter")
                  
#print(a3)
#print(b3)

#Réponse fréquentielle

a=b1aI
b=b1bI
w,H=signal.freqz(b,a)
plt.figure(figsize=(10,5))
plt.title("Réponse fréquentielle b1I")
plt.plot(w/(2*np.pi),20*np.log10(np.absolute(H)))
plt.xlabel("f/fe")
plt.ylabel("Amplitude")
plt.grid()
plt.figure(figsize=(10,5))
plt.title("Réponse fréquentielle b1I")
plt.plot(w/(2*np.pi),np.unwrap(np.angle(H)))
plt.xlabel("f/fe")
plt.ylabel("phase")
plt.grid()
(zeros,poles,gain) = signal.tf2zpk(b,a) 
print(np.absolute(poles))

a=b2aI
b=b2bI
w,H=signal.freqz(b,a)
plt.figure(figsize=(10,5))
plt.title("Réponse fréquentielle b2I")
plt.plot(w/(2*np.pi),20*np.log10(np.absolute(H)))
plt.xlabel("f/fe")
plt.ylabel("Amplitude")
plt.grid()
plt.figure(figsize=(10,5))
plt.title("Réponse fréquentielle b2I")
plt.plot(w/(2*np.pi),np.unwrap(np.angle(H)))
plt.xlabel("f/fe")
plt.ylabel("phase")
plt.grid()
(zeros,poles,gain) = signal.tf2zpk(b,a) 
print(np.absolute(poles))

a=b3aI
b=b3bI
w,H=signal.freqz(b,a)
plt.figure(figsize=(10,5))
plt.title("Réponse fréquentielle b3I")
plt.plot(w/(2*np.pi),20*np.log10(np.absolute(H)))
plt.xlabel("f/fe")
plt.ylabel("Amplitude")
plt.grid()
plt.figure(figsize=(10,5))
plt.title("Réponse fréquentielle b3I")
plt.plot(w/(2*np.pi),np.unwrap(np.angle(H)))
plt.xlabel("f/fe")
plt.ylabel("phase")
plt.grid()
(zeros,poles,gain) = signal.tf2zpk(b,a) 
print(np.absolute(poles))
########
# Réalisation du filtrage
########

# FIR
w1, H1 = signal.freqz(b1F)
w2, H2 = signal.freqz(b2F)
w3, H3 = signal.freqz(b3F)

plt.figure(figsize=(10,4))    
plt.plot(w1/(2*np.pi),20*np.log10(np.absolute(H1)), 'r',
         w2/(2*np.pi),20*np.log10(np.absolute(H2)), 'g',
         w3/(2*np.pi),20*np.log10(np.absolute(H3)), 'b')
plt.title("Réponse fréquentielle de l'assemblage parallèle des trois filtres FIR")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()

# IIR
w1, H1 = signal.freqz(b1bI, b1aI)
w2, H2 = signal.freqz(b2bI, b2aI)
w3, H3 = signal.freqz(b3bI, b3aI)

plt.figure(figsize=(10,4))    
plt.plot(w1/(2*np.pi),20*np.log10(np.absolute(H1)), 'r',
         w2/(2*np.pi),20*np.log10(np.absolute(H2)), 'g',
         w3/(2*np.pi),20*np.log10(np.absolute(H3)), 'b')
plt.title("Réponse fréquentielle de l'assemblage parallèle des trois filtres IIR")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()

#Application des signaux

(fsn, xn) = read('./DATA\Sons\Parole\helpus.wav')

# FIR
y1_fir = signal.convolve(xn,b1F,mode = 'same')
wavfile.write("./FIR/y1_fir.wav", fsn, y1_fir)

y2_fir = signal.convolve(xn,b2F,mode = 'same')
wavfile.write("./FIR/y2_fir.wav", fsn, y2_fir)

y3_fir = signal.convolve(xn,b3F,mode = 'same')
wavfile.write("./FIR/y3_fir.wav", fsn, y3_fir)

plt.figure(figsize=(10,4))    
plt.plot(y1_fir)
plt.title("Sons filtré par b1F")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()

plt.figure(figsize=(10,4))    
plt.plot(y2_fir)
plt.title("Sons filtré par b2F")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()

plt.figure(figsize=(10,4))    
plt.plot(y3_fir)
plt.title("Sons filtré par b3F")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()


# IIR
y1_iir = signal.lfilter(b1bI, b1aI, xn)

wavfile.write("./IIR/y1_iir.wav", fsn, y1_iir)

y2_iir = signal.lfilter(b2bI, b2aI, xn)
wavfile.write("./IIR/y2_iir.wav", fsn, y2_iir)

y3_iir = signal.lfilter(b3bI, b3aI, xn)
wavfile.write("./IIR/y3_iir.wav", fsn, y3_iir)

plt.figure(figsize=(10,4))    
plt.plot(y1_iir)
plt.title("Sons filtré par b1I")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()

plt.figure(figsize=(10,4))    
plt.plot(y2_iir)
plt.title("Sons filtré par b2I")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()

plt.figure(figsize=(10,4))    
plt.plot(y3_iir)
plt.title("Sons filtré par b3I")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()

#Recomposition

plt.figure(figsize=(10,4))    
plt.plot(xn)
plt.title("Signal de base")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()


y_R1fir = 0.333*y1_fir + 0.333*y2_fir + 0.333*y3_fir
wavfile.write("./FIR/yR1_fir.wav", fsn, y_R1fir)
plt.figure(figsize=(10,4))    
plt.plot(y_R1fir)
plt.title("Signal recomposé à part égal")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()

y_R2fir = 0.1*y1_fir + 0.6*y2_fir + 0.3*y3_fir
wavfile.write("./FIR/yR2_fir.wav", fsn, y_R2fir)
plt.figure(figsize=(10,4))    
plt.plot(y_R2fir)
plt.title("Signal recomposé avec 0.1*y1 0.6*y2 0.3*y3")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()

y_R3fir = 0.05*y1_fir + 0.8*y2_fir + 0.15*y3_fir
wavfile.write("./FIR/yR3_fir.wav", fsn, y_R3fir)
plt.figure(figsize=(10,4))    
plt.plot(y_R2fir)
plt.title("Signal recomposé avec 0.05*y1 0.8*y2 0.15*y3")
plt.xlabel("f/fe")
plt.ylabel("GdB")
plt.grid()

w,H=signal.freqz(y_R1fir)
plt.figure(figsize=(10,5)) 
plt.title("Réponse fréquentielle y_R1fir")
plt.plot(w/(2*np.pi),np.unwrap(np.angle(H)))
plt.xlabel("f/fe")
plt.ylabel("phase")
plt.grid()

#%%
### Génération d'un signal bruité
t = np.linspace(-1, 1, 201)
x = (np.sin(2*np.pi*0.75*t*(1-t) + 2.1) + 0.1*np.sin(2*np.pi*1.25*t + 1) + 0.18*np.cos(2*np.pi*3.85*t))
xn = x + np.random.randn(len(t)) * 0.08

# Affichage
plt.figure()
plt.plot(t, x, 'g')
plt.plot(t, xn, 'b')

### Application du filtre FIR b1 à xn
# Convolution centrée
yn = signal.convolve(xn,b1,mode = 'same')
plt.plot(t, yn, 'r')

### Création d'un filtre IIR butter
b, a = signal.butter(3, 0.2)

### Application d'un filtre IIR à xn : 3 versions différentes

#lfilter_zi: to choose the initial condition of the filter:
zi = signal.lfilter_zi(b, a)
z, _ = signal.lfilter(b, a, xn, zi=zi*xn[0])

#Apply the filter again, to have a result filtered at an order the same as filtfilt:
z2, _ = signal.lfilter(b, a, z, zi=zi*z[0])

#Use filtfilt to apply the filter:
y = signal.filtfilt(b, a, xn)

#Plot the original signal and the various filtered versions:
plt.figure()
plt.plot(t, xn, 'b', alpha=0.75)
plt.plot(t, z, 'r--', t, z2, 'r', t, y, 'k')
plt.legend(('noisy signal', 'lfilter, once', 'lfilter, twice','filtfilt'), loc='best')
plt.grid(True)
plt.show()



