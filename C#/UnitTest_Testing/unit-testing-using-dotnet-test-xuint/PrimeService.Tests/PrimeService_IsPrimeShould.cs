using System;
using Xunit;
using Prime.Services;

namespace Tests.Unit
{
    public class PrimeService_IsPrimeShould
    {
        private static readonly PrimeService _primeService = new PrimeService();
        //public PrimeService_IsPrimeShould() => _primeService = new PrimeService();

        public class XUnitTesting
        {
            [Fact]
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
                Assert.False(result, $"{value} should not be prime");
            }
        }
    }
}
