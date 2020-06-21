# Range: 1 : 10,000
# Time:  13.548s

# Written by Alex Vear in 2019
# Public domain.  No rights reserved.

startval = 1
endval = 10000

for num in startval:endval
  sqr = num^2
  for i in startval:endval
    tri = (i*(i+1))/2
    if sqr == tri
      println("Square Number: $sqr Triangle Number: $tri Squared: $num Triangled: $i")
    end
  end
end
