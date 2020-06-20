# Written in 2017 by Alex Vear.
# Public domain.  No rights reserved.

my $width = 18;
my $height = 2;
my $max = 14;

my $message = "klsdjnvvlk kv lksdjd vlsdndn vlaksdnkf  sdf.";

my $div = length($message) / $max;

print "$div" . "\n";

print "\n";

# --------------------------------------------------------------

my $mess = "hello world this is testing \n"
. "and this is further testing\n"
. "et even more testing.";

my @message_new = split /\n/, $mess;

print "$#message_new". "\n";
