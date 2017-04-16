#include <iostream>

// function returns an integer value
// so `int` is used before the function name
int returnInteger()
{
  // NOTE: can also return an integer variable
  return 42;
}


void testFunc()
{
  std::cout << "Printing content in testFunc()" << std::endl;
  // void <-- expect no return value
}


int addParams(int x, int y, int z)
{
  return x + y + z;
}


int main()
{
  std::cout << "Running main()" << std::endl;
  std::cout << "Running function testFunc()" << std::endl;
  testFunc();
  std::cout << "Ending main()" << std::endl;

  return 0;
}
