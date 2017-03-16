#Rock, Paper, Scissors - Core
#rps_core_py3.py for python 3.4 | Written by Alex Vear
#Written: October 2015 | Modified: 22nd December 2015

import random #imports the random module.
player = len(input("Rock, Paper or Scissors\n"))
#asks the player for an input then converts it,
#into the length of the word.
computer = random.choice(["Rock", "Paper", "Scissors"])
#random choice for computer.
print("Computer picked: ", computer)
computer = len(computer)
#outputs the computer's choice then,
#converts the choice to number based upon length.
if player == computer: print("You Drew")
elif player == 8 and computer == 4: print("You lose")
elif player > computer or (player == 4 and computer == 8): print("You win")
else: print("You lose")
#if statements to control who wins the game.
