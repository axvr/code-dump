#Rock, Paper, Scissors - Advanced
#rps_adv_py2.py for python 2.7 | Written by Alex Vear
#Written: 22nd December 2015 | Modified: 22nd December 2015

import random #imports the random module.
playAgain='y'
while playAgain=='y' or playAgain=='Y':
	win=0
    #While loop so player can be asked if they want to play again.
    print "\nScissors cuts Paper \nPaper covers Rock \nRock crushes Scissors."
    #prints the rules of the game.
    for i in range(0,3):
        #allows for three rounds each game.
        player = len(raw_input("\nRock, Paper or Scissors\n\nPlayer picks: "))
        #asks the player for an input then converts it,
        #into the length of the word.
        computer = random.choice(["Rock", "Paper", "Scissors"])
        #random choice for computer.
        print "Computer picked: ", computer
        computer = len(computer)
        #outputs the computer's choice then,
        #converts the choice to number based upon length.
        if player == computer: print "You Drew"
        elif player == 8 and computer == 4: print "You lose"
        elif player > computer or (player == 4 and computer == 8):
            print "You win"
            win=win+1
        else: print "You lose"
        #if statements to control who wins the game.
    print '\nYour Score: ', win, '/3\n'
    #Outputs number of times the player has won.
    playAgain=raw_input("Would you like to play again? 'y' or 'n'\n")
print '\nThanks for playing.\n'
