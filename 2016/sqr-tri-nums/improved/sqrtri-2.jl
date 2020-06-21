# Range: 1 : 100,000
# Time:  14m26.060s

# Written by Alex Vear in 2019
# Public domain.  No rights reserved.

startval = 1
endval = 100000

for num in startval:endval
  sqr = 2(num^2)
  for i in startval:endval
    tri = i*(i+1)
    if sqr == tri
      sqr = sqr/2
      tri = tri/2
      println("Square Number: $sqr Triangle Number: $tri Squared: $num Triangled: $i")
    end
  end
end
