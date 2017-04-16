// Comment (same as in normal C)

/* multi
   line
   comment
*/

#include <iostream>

int main()
{
  // typically place the comments above the line
  // which is being commented
  std::cout << "Hello world!" << std::endl;
  return 0; // end of line comments can make code difficult to read
}

// compile using `g++ -o helloworld helloworld.cpp`
// execute using `./helloworld`

