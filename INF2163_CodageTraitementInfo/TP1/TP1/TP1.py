import numpy as np
from random import random
from operator import add
import time
n = int(input("Entrez un entier : "))
l1 = [random() 5for i in range(n)]
l2 = [random() for i in range(n)]
start = time.clock()
l3 = map(add, l1, l2)
end = time.clock()
print( end - start)
A1 = np.array(l1)
A2 = np.array(l2)
start = time.clock()
A3 = A1 + A2
end = time.clock()
print( end - start)

#%%
import numpy as np
import matplotlib.pyplot as plt
x = np.array([0,1,2,3])
v = np.array([1,3,2,4])
print(v)
print(type(v))
fig = plt.figure()
plt.plot(x,v,'rv--', label = 'v(x)')
plt.legend(loc='lower rig')
plt.xlabel('x')
plt.ylabel('v')
plt.title('Mon titre')
plt.xlim([-1,4])
plt.ylim([0,5])
plt.show()
fig.savefig('toto.png')

#%%
import numpy as np
v = np.array([1.5])
print(v)
print(v.dtype)
M = np.array([[1,2],
             [3,4]])
print(M)
print(M.dtype)
#M[0,0] = "hello" #Ne fonctionne pas car pas de cast string vers int
print(M)
print(type(v))
print(type(M))
print(v.shape)
print(M.shape)
 #%%
 import numpy as np
a=np.array([1,2,3])
a[0] = 3.2 #Cast du float en int
print(a)
a.dtype

#%%
import numpy as np
np.arange(10)
np.linspace(0,9,dtype=int, num=10)
np.logspace(0,1,dtype=int, num=10)

#%%
import numpy as np
import matplotlib.pyplot as plt

M = np.random.randn(3,3)
plt.hist(M,40)
a = np.random.randn(10000)
plt.hist(a,40)

#%%
import numpy as np
zero = np.zeros(3, int)
print(zero)
np.ones([3,3], int)
np.diag([1,2,3])
np.identity(3)

np.full([3,5], np.nan)

#%%
import numpy as np
def initfunction(i,j):
    return 100 + 10*i + j

np.fromfunction(initfunction, [3,5])
#%%
import numpy as np
arr = np.ones([3,5,7], int)
np.save("save.txt",arr)
loadArr = np.load("save.txt")
print(loadArr)
#%%
import numpy as np
A = np.arange(8).reshape([4,2])
B = np.arange(6).reshape([2,3])
print(A)
print(B)
print(A[2,1])
print(B[1,2])

M = np.random.randn(3,3)
print(M[2,1])
print(M[1,2])
M[2,1] = 42
print(M[2,1])
print(M[1:2:1])

#%%
import numpy as np
A = np.arange(6).reshape([3,2])
B = np.arange(6).reshape([3,2])
print(A+B)
print(A*3)
print(B/4)

u = np.array([1,2,3])
v = np.array([4,5,6])
w = np.array([7,8,9])
print(2*v-3*w+u/3)

#%%
import numpy as np

def multiply(A,B):
    return np.dot(A,B)

A = np.arange(4).reshape([2,2])
B = np.arange(4).reshape([2,2])
print(multiply(A,B))
































