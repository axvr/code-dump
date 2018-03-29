using Pong.Events;
using System;
using System.Threading.Tasks;

namespace Pong
{
    class Program
    {
        static void Main(string[] args)
        {

            Console.WriteLine("Pong!");
            Console.WriteLine("Press any key to begin... (or q to quit)");
            ConsoleKeyInfo keyIntercept = Console.ReadKey(true);

            // Instantiate objects
            Court Level1 = new Court();
            Paddle Player1 = new Paddle(percentHeight: 10, percentWidth: 2);
            Ball Ball1 = new Ball(percentSquareDimention: 2);

            //Level1.CourtRefresh += new CourtRefresh(Player1.Echo);

            if (keyIntercept.KeyChar != 'q')
            {

                Level1.DrawCourt();

                do
                {
                    switch (keyIntercept.KeyChar)
                    {
                        case 'k':
                            break;
                        case 'j':
                            break;
                        case 'p':
                            break;
                        default: break;
                    }

                    keyIntercept = Console.ReadKey(true);
                } while (keyIntercept.KeyChar != 'q');

            }

        }
    }
}
