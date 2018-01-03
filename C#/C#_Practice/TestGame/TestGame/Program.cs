using System;

// The Not Quite a Game Yet, Game.
// -------------------------------
// 
// This is just C# & OOP Practice
// (it will never be finished).
namespace TestGame
{

    enum ComputerMovement
    {
        Up = 1,
        Down,
        Left,
        Right,
        Nothing,
    }

    class Program
    {
        static void Main(string[] args)
        {

            Map firstMap = new Map();
            Player player1 = new Player(StartPosition: new int[] { 6, 6, },
                                        Piece: '@',
                                        CurrentMap: firstMap);

            Player computer1 = new Player(StartPosition: new int[] { 3, 2, },
                                        Piece: '*',
                                        CurrentMap: firstMap);

            firstMap.CreatePiece(Where: new int[] { 3, 6 }, Piece: '#');

            bool exit = false;
            string command;

            Random rand = new Random();

            do
            {
                firstMap.RedrawMap();

                Console.Write(new String(' ', Console.BufferWidth - 1));
                Console.Write("\r");

                // replace with key press events
                command = Console.ReadLine().ToLower();
                switch (command)
                {
                    case "up":
                        player1.MoveUp(CurrentMap: firstMap);
                        break;
                    case "down":
                        player1.MoveDown(CurrentMap: firstMap);
                        break;
                    case "left":
                        player1.MoveLeft(CurrentMap: firstMap);
                        break;
                    case "right":
                        player1.MoveRight(CurrentMap: firstMap);
                        break;
                    case "exit":
                        exit = true;
                        break;
                    default:
                        break;
                }
                // TODO optimise this (don't use duplicate code)
                int random = rand.Next(1, 5);
                switch (random)
                {
                    case (int)ComputerMovement.Up:
                        computer1.MoveUp(CurrentMap: firstMap);
                        break;
                    case (int)ComputerMovement.Down:
                        computer1.MoveDown(CurrentMap: firstMap);
                        break;
                    case (int)ComputerMovement.Left:
                        computer1.MoveLeft(CurrentMap: firstMap);
                        break;
                    case (int)ComputerMovement.Right:
                        computer1.MoveRight(CurrentMap: firstMap);
                        break;
                    default:
                        break;
                }

            } while (exit == false);

        }
    }
}
