using System;
using System.Collections.Generic;
using System.Text;

namespace TestGame
{
    class Map
    {
        public Map()
        {
            // TODO populate a 2D array using params specifing dimentions
            // Allow adding a border and customising it
            map = new char[12, 24] {
                { '┌', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '┐', },
                { '│', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '│', },
                { '│', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '│', },
                { '│', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '│', },
                { '│', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '│', },
                { '│', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '│', },
                { '│', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '│', },
                { '│', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '│', },
                { '│', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '│', },
                { '│', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '│', },
                { '│', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '│', },
                { '└', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '─', '┘', },
            };
        }

        public void RedrawMap()
        {
            int rowLength = this.map.GetLength(0);
            int colLength = this.map.GetLength(1);

            Console.SetCursorPosition(0, 0);

            for (int i = 0; i < rowLength; i++)
            {
                for (int j = 0; j < colLength; j++)
                {
                    Console.Write(string.Format("{0}", this.map[i, j]));
                }
                Console.Write(Environment.NewLine);
            }
        }

        public bool UpdateMaps(int[] From, int[] To)
        {
            // use previous map to check if player is on an object

            // What is this? LISP?
            if (((To[0] > 0) && (To[0] <= 10)) &&
                ((To[1] > 0) && (To[1] <= 22)))
            {
                char piece = map[From[0], From[1]];
                map[To[0], To[1]] = piece;
                map[From[0], From[1]] = ' ';

                return true;
            }
            else
            {
                return false;
            }

        }

        public bool CreatePiece(int[] Where, char Piece)
        {
            if (map[Where[0], Where[1]] == ' ')
            {
                map[Where[0], Where[1]] = Piece;
                return true; 
            }
            else
            {
                return false;
            }
        }

        public char[,] map;

    }
}
