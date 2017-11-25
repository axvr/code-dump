using System;
using System.Collections.Generic;
using System.Text;

namespace Pong
{
    class Ball : Entity
    {
        // Inherited Constructor
        public Ball(int blockSquareDimention)
            : base(blockSquareDimention) { }
        public Ball(float percentSquareDimention)
            : base(percentSquareDimention) { }

    }
}
