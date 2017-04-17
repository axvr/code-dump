#include <iostream>

int add(int a, int b); // forward declaration of existence of add()

int main() {
  std::cout << "The sum of: 20 + 22 = " << add(20, 22) << std::endl;
}

// g++ multi-file1.cpp multi-file2.cpp -o multi-file
