#%%
#%%
#A exÃ©cuter avant
import numpy as np
import matplotlib.pyplot as plt

#%%
def serieFourier (ordre, N) :         
    R = np.zeros(N);     
    t = np.linspace(0,((N-1)/N)*2*(np.pi),N);     
    for i in range(N):         
        x=t[i]         
        # calcul de gj(x), avec j variant de 1 à ordre  
        y = 0         
        for k in range(1,ordre+1):
            if k%2 == 0: #Pair
                terme = 2*np.pi;
            else: #Impair
                terme = (2*np.pi)+(4/(k*x*np.pi))*np.sin((2*np.pi*k*x)*2*np.pi);
            y = y + terme;         
        R[i]=y

    return R; 

#sf = serieFourier(20, 1000)        
#plt.plot(sf)

for i in range(1, 100, 3):
  plt.plot(serieFourier(i, 100))