#!/bin/awk -f

# Extract and output unique metadata tags.

# USAGE:
#   $ ./scripts/metatags.awk "test.html"

BEGIN { FS=":" }

/<!-- METADATA/,/-->/ {
    # Remove the delimiters
    gsub(/(<!-- METADATA|-->)/, "")

    # Check line contains a unique tag
    if (!/:/ || seen[$1]++) next

    # Trim trailing/leading whitespace
    gsub(/(^[ \t]+|[ \t]+$)/, "")

    print
}
