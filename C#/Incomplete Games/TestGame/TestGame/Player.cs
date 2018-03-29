using System;
using System.Collections.Generic;
using System.Text;

namespace TestGame
{
    class Player
    {
        public Player(int[] StartPosition, char Piece, Map CurrentMap)
        {
            CurrentPosition = StartPosition;
            _piece = Piece;
            CurrentMap.CreatePiece(Where: StartPosition, Piece: Piece);
        }

        // TODO specify blocks (chars) which can be passed through

        // TODO optimise the code below (too much duplicate code)
        public void MoveUp(Map CurrentMap)
        {
            // specify amount of sqaures to move by
            if (CurrentMap.UpdateMaps(From: CurrentPosition,
                To: new int[] { CurrentPosition[0] - 1, CurrentPosition[1] }))
            {
                CurrentPosition[0] = CurrentPosition[0] - 1;
            }
        }

        public void MoveDown(Map CurrentMap)
        {
            // specify amount of sqaures to move by
            if (CurrentMap.UpdateMaps(From: CurrentPosition, 
                To: new int[] { CurrentPosition[0] + 1, CurrentPosition[1] }))
            {
                CurrentPosition[0] = CurrentPosition[0] + 1;
            }
        }

        public void MoveRight(Map CurrentMap)
        {
            // specify amount of sqaures to move by
            if (CurrentMap.UpdateMaps(From: CurrentPosition, 
                To: new int[] { CurrentPosition[0], CurrentPosition[1] + 1 }))
            {
                CurrentPosition[1] = CurrentPosition[1] + 1;
            }
        }

        public void MoveLeft(Map CurrentMap)
        {
            // specify amount of sqaures to move by
            if (CurrentMap.UpdateMaps(From: CurrentPosition, 
                To: new int[] { CurrentPosition[0], CurrentPosition[1] - 1 }))
            {
                CurrentPosition[1] = CurrentPosition[1] - 1;
            }
        }

        public void TakeDamage()
        {
        }

        public int[] CurrentPosition { get; }
        private float _health = 100;
        private char _piece;
        public float Health { get { return _health; } }

    }
}
