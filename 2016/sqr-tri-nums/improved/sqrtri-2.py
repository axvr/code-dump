# Range: 1 : 100,000
# Time:  28m20.028s

# Written by Alex Vear in 2019
# Public domain.  No rights reserved.

startval = 1
endval = 100000

for num in range(startval, endval):
    sqr = (num*num)*2
    for i in range(startval, endval):
        tri = i*(i+1)
        if sqr == tri:
            sqr = sqr/2
            tri = tri/2
            print('Square Number:', sqr, ' Triangle Number:', tri, ' Squared:', num, ' Triangled:', i)
