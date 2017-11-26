using System;

namespace Pong
{
    abstract class Entity
    {
        public Entity(float percentHeight, float percentWidth)
        {
            if (percentHeight >= 0 && percentWidth >= 0)
            {
                int Height = (int)((Console.BufferHeight / 100) * percentHeight);
                int Width  = (int)((Console.BufferWidth  / 100) * percentWidth);
            }
        }
        public Entity(int blockHeight, int blockWidth)
        {
            if (blockHeight >= 0 && blockWidth >= 0)
            {
                Height = blockHeight;
                Width = blockWidth;
            }
        }
        public Entity(int blockSquareDimention)
        {
            if (blockSquareDimention >= 0)
            {
                Height = Width = blockSquareDimention;
            }
        }
        public Entity(float percentSquareDimention)
        {
            if (percentSquareDimention >= 0)
            {
                percentSquareDimention = 
                    (int)((Console.BufferHeight / 100) * percentSquareDimention);
                Height = Width = (int)percentSquareDimention;
            }
        }

        //private char[,] CalculateShape()
        //{
        //    for (int i = startPosition[0]; i < startPosition[0] + Width; i++)
        //    {
        //        for (int j = startPosition[0]; j < startPosition[0] + Width; j++)
        //        {
        //            entity[i, j] = '#';
        //        }
        //    }
        //}

        private int[] startPosition;
        private char[,] entity;
        public int Height { get; }
        public int Width { get; }
    }
}
