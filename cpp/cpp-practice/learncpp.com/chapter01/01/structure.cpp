// Structure of a Program

#include <iostream> // preprocessor directive (make compiler do a specific task)

int main() {

  // Statements

  /* the smallest independent unit in the language
     often terminated by a semicolon
     tells the compiler to perform a particular task
  */

  int x;                       // declaration statement
  x = 5;                       // assignment statement
  std::cout << x << std::endl; // output statement
  std::cout << "Hello World!" << std::endl;

  // Expressions

  /* mathematical entity which evaluates to a value */

  x = 2 + 3;

  // Functions

  /* A collection of statements that execute sequentially.
     the function "main" is included in every C++ program.
  */

  // Libraries

  /* collection of precompiled code for use in many different programs
     they provide extra functionality for the program.
  */

  std::cout << x << std::endl;

  return 0; // return statement
}
