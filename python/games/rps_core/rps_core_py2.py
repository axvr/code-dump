#Rock, Paper, Scissors - Core
#rps_core_py2.py for python 2.7 | Written by Alex Vear
#Written: September 2015 | Modified: 22nd December 2015

import random #imports the random module.
play = len(raw_input("Rock, Paper or Scissors\n"))
#asks the player for an input then converts it,
#into the length of the word.
opponent = random.choice(["Rock", "Paper", "Scissors"])
#random choice for computer.
print "Computer picked: ", opponent
opponent = len(opponent)
#outputs the computer's choice then,
#converts the choice to number based upon length.
if play == opponent: print "You Drew"
elif play == 8 and opponent == 4: print "You lose"
elif play > opponent or (play == 4 and opponent == 8): print "You win"
else: print "You lose"
#if statements to control who wins the game.
