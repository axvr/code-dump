using System;
using System.Text.RegularExpressions;

namespace RegexTesting
{
    class Program
    {
        static void Main(string[] args)
        {

            Console.Title = "Regex Testing";

            string[] numbers =
            {
                "123-555-0190", "444-234-22450",
                "690 555 0178", "146-893-232",
                "146-555-0122", "4007 555 0111",
                "407-555-0111", "407-2-5555",
            };

            string Pattern = @"^(\d{3}(?: |-)\d{3}(?: |-)\d{4})$";

            foreach (string number in numbers)
            {
                Console.Write("{0,24}", number);

                if (Regex.IsMatch(number, Pattern, RegexOptions.Singleline))
                {
                    Console.WriteLine(" - valid");
                }
                else { Console.WriteLine(" - invalid"); }
            }

            string testString = "The quick brown fox jumped over the lazy dog.\n" +
                "Hello World! Foo Bar.";
            Console.WriteLine(testString);
            string[] splitString = Regex.Split(testString, @"\s+");
            foreach (string split in splitString)
            {
                Console.WriteLine(split);
            }

            string someString = "ThiS iS     a test of thE Regex   replacEment   fuNctioNaliTy iN C# .NET";
            Console.WriteLine(someString);
            string replaceString = Regex.Replace(someString, @"\B[A-Z]", m => m.ToString().ToLower());
            replaceString = Regex.Replace(replaceString, @" {2,}", " ");
            Console.WriteLine(replaceString);

            // Other C# Testing
            Console.WriteLine(Environment.NewLine);


            // You can create variables/identifiers which preceding with an '@' symbol
            // to use reserved keywords as variables
            int @if = 2;
            Console.WriteLine(@if);

            /* Different types in C#:
             *      Value Types
             *      Reference Types
             *      Pointer Types
             * 
             * Value Types: 
             * 
             * 
             * Reference Types:
             * 
             * 
             * 
             * Pointer Types: 
             * 
             * 
             * 
             * 
             */



            // Defining a 6-tuple in C#
            var tuple = new Tuple<string, int, int, double, decimal, float>(
                "Hello World", 1, 2, 5, (decimal)2.4, (float)4.2);
            // Same tuple but simpler creation
            var newTuple = Tuple.Create("Hello World", 1, 2, 5, (decimal)2.4, (float)4.2);


            // Formatting strings





            Console.Write("Press any key to exit..."); Console.ReadKey();

        }
    }
}
