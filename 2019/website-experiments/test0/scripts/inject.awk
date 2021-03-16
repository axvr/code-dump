#!/bin/awk -f

# Replace a token with a new value.

# USAGE:
#   $ echo "This is a {{token}}." | awk -f scripts/inject.awk "token" "new value"
#   This is a new value.

BEGIN {
    token=ARGV[1]
    content=ARGV[2]
    delete ARGV[1]
    delete ARGV[2]
}

{
    # FIXME: doesn't correctly handle ampersand characters (in the meantime
    # triple escape using backslashes).

    gsub("{{\\s*" token "\\s*}}", content)
    print
}
