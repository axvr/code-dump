#!/bin/sh

for i in $(seq 1 1000000)
do
    x=0
    [ $(($i % 3)) = 0 ] && printf "Fizz" && x=1
    [ $(($i % 5)) = 0 ] && printf "Buzz" && x=1
    [ $x = 0 ] && printf $i
    printf "\n"
done
