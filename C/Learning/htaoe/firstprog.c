#include <stdio.h>

int main()
{
    int i;
    for(i=0; i < 10; i++)           // Loop 10 times.
    {
        printf("Hello, world!\n");  // Print the string to stdout.
    }
    return 0;                       // Tell the OS the program exited without errors.

    /* Alternative:
     *
     *  for(i=0; i<10; i++)
     *       printf("Hello, world!\n");
     *  return 0;
     */
} 
