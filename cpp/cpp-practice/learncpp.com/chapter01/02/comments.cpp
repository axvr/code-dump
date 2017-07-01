// Comments

/* A line or multiple lines which explain what the code is doing
 there are two types of comments: multi-line and single line
*/

// Single line comment!!! everything from the right of here is ignored
// by the compiler

int x; // declare the variable x

/* Multiple line comment!!!
   everything until the closing comment is ignored by the compiler
*/

/* The single line comment is usually used to make a quick comment
   about a line of code. The single line comment is most often placed
   above the line it is commenting. This improves readability over
   them being placed at the end of the line
*/

int y; // difficult to read, especially with long lines

// easier to read, even with longer lines
int z;

/* --------------------------------------------------------------------------- */

/* Comments should be used for 3 things:
 * 1. Libraries, programs & functions --> describe what it does,
 * 2. Within a library, function or program --> describe how it will accomplish it's goal,
 * 3. Statements --> explain why the code is doing something
 */

/* --------------------------------------------------------------------------- */

// Examples of 1.

// This function calculates student's final grade based on test and homework scores.

// The following lines generate a random item based on rarity, level, and a weight factor.

/* --------------------------------------------------------------------------- */

// Examples of 2.

// To generate a random item, we're going to do the following:
// 1) Put all of the items of the desired rarity on a list
// 2) Calculate a probability for each item based on level and weight factor
// 3) Choose a random number
// 4) Figure out which item that random number corresponds to
// 5) Return the appropriate item

/* To calculate the final grade, we sum all the weighted midterm and homework scores
   and then divide by the number of scores to assign a percentage.  This percentage is
   used to calculate a letter grade. */

/* --------------------------------------------------------------------------- */

// Examples of 3.

// The player just drank a potion of blindness and can not see anything
sight = 0;

/* --------------------------------------------------------------------------- */

// Examples of bad comments & good alternatives

// Bad

// Set sight range to 0
sight = 0;

// Good

// The player just drank a potion of blindness and can not see anything
sight = 0;

// -------------------------------------

// Bad

// Calculate the cost of the items
cost = items / 2 * storePrice;

// Good

// We need to divide items by 2 here because they are bought in pairs
cost = items / 2 * storePrice;

/* --------------------------------------------------------------------------- */

// Good Comments

// We decided to use a linked list instead of an array because
// arrays do insertion too slowly.

// We're going to use newton's method to find the root of a number because
// there is no deterministic way to solve these equations.

/* --------------------------------------------------------------------------- */

// Summary

/* At the library, program, or function level, describe what
 * Inside the library, program, or function, describe how
 * At the statement level, describe why.
 */

/* --------------------------------------------------------------------------- */

// You can also comment out code for various different reasons
