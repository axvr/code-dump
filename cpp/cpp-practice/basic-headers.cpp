#include "basic-headers.h"
#include <iostream>

int main() {
  std::cout << "The sum of: 20 + 22 = " << add(20, 22) << std::endl;
  return 0;
}

// compile using:
// g++ basic-headers.cpp basic-headers2.cpp -o basic-headers

// specify an alternative include directory
// g++ -o main -I /source/includes main.cpp
