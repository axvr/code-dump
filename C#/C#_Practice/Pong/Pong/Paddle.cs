using System;
using System.Collections.Generic;
using System.Text;

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


    }
}
