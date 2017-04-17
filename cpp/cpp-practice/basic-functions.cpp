#include <iostream>

// function returns an integer value so `int` is used before the function name
// NOTE: can also return an integer variable
int returnInteger() { return 42; }

// void <-- expect no return value
void testFunc() { std::cout << "Printing content in testFunc()" << std::endl; }

int addParams(int x, int y, int z); // forward declaration of function

int main() {
  std::cout << "Running main()" << std::endl;

  std::cout << "Running function testFunc()" << std::endl;
  testFunc();

  std::cout << addParams(1, 2, 3) << std::endl;

  std::cout << "Ending main()" << std::endl;

  return 0;
}

int addParams(int x, int y, int z) { return x + y + z; }
