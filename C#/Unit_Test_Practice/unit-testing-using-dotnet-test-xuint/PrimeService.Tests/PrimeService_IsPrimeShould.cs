using System;
using Xunit;
using Prime.Services;

/*
 * xUnit:
 * 
 * [Fact]
 * [Theory]
 * 
 * Xunit.Assert.
 * 
 *
 * Other Flags (TODO research these):
 *
 * [TestPriority()]
 * 
 * [InlineData(var)]
 * [MemberData(object)]
 * 
 * [AttributeUsage()]
 * 
 * [Observation]
 * 
 * [CollectionDefinition()]
 * [Collection()]
 * 
 * [TestCaseOrderer()]
 * [TestCollectionOrderer()]
 * [TestFramework()]
 * 
 * [SkippableFact]
 * [SkippableTheory]
 */

namespace Tests.Unit
{
    public class PrimeService_IsPrimeShould
    {
        private static readonly PrimeService _primeService = new PrimeService();
        //public PrimeService_IsPrimeShould() => _primeService = new PrimeService();

        public class XUnitTesting
        {
            [Fact]
            [Obsolete("Test of making a method obsolete", true)]
            public void ReturnFalseGivenValueOf1()
            {
                //var _primeService = new PrimeService();
                var result = _primeService.IsPrime(1);
                Assert.False(result, "1 should not be prime");
            }

            [Theory]
            [InlineData(-1)]
            [InlineData(0)]
            [InlineData(1)]
            public void ReturnFalseGivenValuesLessThan2(int value)
            {
                //var _primeService = new PrimeService();
                var result = _primeService.IsPrime(value);
                //ReturnFalseGivenValueOf1();
                Assert.False(result, $"{value} should not be prime");
            }

            [Theory, InlineData(3), InlineData(5),
                InlineData(100)]
            public void ReturnTrueGivenValuesGreaterThan2(int value)
            {
                var result = _primeService.IsPrime(value);
                Assert.True(result, $"{value} should be greater than 2");
            }
        }

        public class TestingOfxUnit
        {
            [Fact]
            public void RandomTest()
            {
                Assert.Equal(1, 1);
            }

            [Theory]
            [InlineData(1, 41)]
            [InlineData(2, 40)]
            public void AdditionTest(int a, int b)
            {
                // The answer to life, the universe and everything: 42
                var result = a + b;
                Assert.Equal(result, 42);
            }
        }
    }
}
