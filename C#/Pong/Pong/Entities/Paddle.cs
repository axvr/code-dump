using System;
using System.Collections.Generic;
using System.Text;
using Pong.Events;

namespace Pong
{
    class Paddle : Entity
    {
        // Inherited Constructor
        public Paddle(float percentHeight, float percentWidth)
            : base(percentHeight, percentWidth) { }
        public Paddle(int blockHeight, int blockWidth)
            : base(blockHeight, blockWidth) { }

        public void MoveUp()
        {

        }

        public void MoveDown()
        {

        }

        public void Echo(object sender, CourtRefreshEventArgs args)
        {
            Console.WriteLine("Hello World");
        }


    }
}
