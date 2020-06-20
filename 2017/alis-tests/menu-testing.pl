# Written in 2017 by Alex Vear.
# Public domain.  No rights reserved.

use strict;
use warnings;

#sub menu {

#    my $title = $_[0];
#    my $message = $_[1];
#    my @items = @_;
#
#    shift(@items);
#    shift(@items);
#
#    print("$title \n");
#    print("$message \n");
#    print("@items \n");


#    my $whiptail = qq{ whiptail --nocancel }.
#        qq{ --title "$title" }.
#        qq{ --menu "$message" }.
#        qq{ }.
#        qq{ 15 55 7 }.
#        qq{ 3>&1 1>&2 2>&3 };
#    return `$whiptail`;

sub menu {
    # TODO Menu selection screens
    my @items = @_;
    my $title = shift(@items);
    my $message = shift(@items);

    my $whiptail = qq{ whiptail }. qq{ --nocancel }.
        qq{ --title }. qq{ "$title" }.
        qq{ --menu }. qq{"\n$message" }.
        qq{ 16 }. qq{ 55 }. qq{ 7 }.
        qq{ @items }.
        qq{ 3>&1 }. qq{ 1>&2 }. qq{ 2>&3 };
    return `$whiptail`;
}



my $title = "ALIS Main Menu";
my $message = "Please pick an item from the list below.";
my $item1 = qq{ "Pre-installation" "Set up prior to installation" };
my $item2 = qq{ "Installation" "Install Arch to your system" };
my $item3 = qq{ "Configuration" "Configure your Arch install" };
my $item4 = qq{ "Post-Installation" "Post-install set up" };
my $item5 = qq{ "About ALIS" "Information on ALIS" };
my $item6 = qq{ "Quit / Cancel" "Close ALIS" };

my $test = menu("$title", "$message", "$item1", "$item2", "$item3",
                "$item4", "$item5", "$item6");

print("$test \n");
