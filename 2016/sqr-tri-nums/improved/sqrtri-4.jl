# Range: 1 : 100,000,000
# Time:  36.494s

# Written by Alex Vear in 2019
# Public domain.  No rights reserved.

startval = 1
endval = 100000000

sqrs = [x^2 for x in startval:endval]
tris = [x*(x+1)/2 for x in startval:endval]
println(intersect(sqrs, tris))
