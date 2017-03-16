# Random code to find which triangle numbers and which square numbers are the same | Written 13 Jul 2016 by Alex Vear

startValue=1      #set the start value for the program to test
endValue=1000000  #set the end value for the program to test
for num in range (startValue,endValue):
  sqr=num*num
  for i in range (startValue,endValue):
    tri=((i*(i+1))/2)
    if sqr == tri:
      print('Square Number:', sqr, ' ', 'Triangle Number:', tri, ' ', 'Squared:', num, ' ', 'Triangled:', i)
    else: continue
