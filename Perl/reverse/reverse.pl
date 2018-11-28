#!/usr/bin/env perl

use strict;
use warnings;
use integer;

# Reverse a number mathematically
sub reverse {
    my $reverse = 0;
    my $number = $_[0];

    while ($number > 0) {
        my $last_digit = $number % 10;
        $reverse = ($reverse * 10) + $last_digit;
        $number = $number / 10;
    }

    return $reverse;
}

# print("Enter a number: ");
# my $number = <STDIN>;

my $number = 123456789123456789;

print(main::reverse($number));
