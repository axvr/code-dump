#include <iostream>

int main()
{
  int value;
  std::cout << "Enter a number: ";
  std::cin >> value;
  // end line already inputted by the user
  //std::cout << std::endl;

  // output the result that the user gave as an input
  std::cout << "You inputted: " << value << std::endl;

  return 0;
}
