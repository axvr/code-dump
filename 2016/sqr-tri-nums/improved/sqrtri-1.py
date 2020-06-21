# Range: 1 : 10,000
# Time:  22.672s

# Written by Alex Vear in 2019
# Public domain.  No rights reserved.

startval = 1
endval = 10000

for num in range(startval, endval):
    sqr = num*num
    for i in range(startval, endval):
        tri = (i*(i+1))/2
        if sqr == tri:
            print('Square Number:', sqr, ' Triangle Number:', tri, ' Squared:', num, ' Triangled:', i)
