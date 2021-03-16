#!/bin/awk -f

# Get the value of a meta data field.

BEGIN {
    FS=":"
    key=ARGV[1]
    delete ARGV[1]
}

{
    if ($1 != key) next
    sub("^" key FS "\\s*", "", $0)
    print
}
