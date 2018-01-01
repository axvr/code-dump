using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Pong
{
    class Court
    {

        public Court()
        {

            Height = Console.WindowHeight - 1;
            Width = Console.WindowWidth - 1;

            View = Grid = CreateCourt(DimentionX: Height, DimentionY: Width);

        }

        //public bool DrawEntity(int[] Location, Entity Entity)
        //{
        //}

        public bool MoveEntity()
        {

            return true;
        }

        public async void DrawCourt()
        {
            await Task.Run(() =>
            {

                while (pause)
                {
                    int rowLength = Grid.GetLength(0);
                    int colLength = Grid.GetLength(1);

                    Console.SetCursorPosition(0, 0);

                    for (int i = 0; i < rowLength; i++)
                    {
                        for (int j = 0; j < colLength; j++)
                        {
                            Console.Write(string.Format("{0}", View[i, j]));
                        }
                        Console.Write(Environment.NewLine);
                    }

                    Thread.Sleep(500);
                }
            });

        }

        private char[,] CreateCourt(int DimentionX, int DimentionY)
        {
            char[,] temporaryCourt = new char[DimentionX, DimentionY];

            for (int i = 0; i < DimentionX; i++)
            {

                for (int j = 0; j < DimentionY; j++)
                {
                    if (i == 0 && j == 0)
                    {
                        temporaryCourt[i, j] = '┌';
                    }
                    else if (i == 0 && j == DimentionY - 1)
                    {
                        temporaryCourt[i, j] = '┐';
                    }
                    else if (i == DimentionX - 1 && j == 0)
                    {
                        temporaryCourt[i, j] = '└';
                    }
                    else if (i == DimentionX - 1 && j == DimentionY - 1)
                    {
                        temporaryCourt[i, j] = '┘';
                    }
                    else if (i == 0 || i == DimentionX - 1)
                    {
                        temporaryCourt[i, j] = '─';
                    }
                    else if (j == 0 || j == DimentionY - 1)
                    {
                        temporaryCourt[i, j] = '│';
                    }
                    else
                    {
                        temporaryCourt[i, j] = ' ';
                    }
                }

            }

            return temporaryCourt;
        }


        private char[,] Grid;
        private char[,] View;

        public int Height { get; }
        public int Width { get; }

        private bool pause = true;

    }
}
