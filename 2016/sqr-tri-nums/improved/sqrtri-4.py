# Range: 1 : 100,000,000
# Time:  1m14.893s

# Written by Alex Vear in 2019
# Public domain.  No rights reserved.

startval = 1
endval = 100000000

sqrs = [x*x for x in range(startval, endval)]
tris = [x*(x+1)/2 for x in range(startval, endval)]
print(set(sqrs).intersection(tris))
