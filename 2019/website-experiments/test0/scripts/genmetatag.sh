#!/bin/sh

# Generate HTML meta tags.

# USAGE:
#   - When given with no arguments, this script expects input from STDIN in the
#     same format as is produced by `./scripts/metatags.awk`.
#   - When given 2 arguments (and the first is not empty), a single HTML meta
#     tag will be produced.
#   - Otherwise do nothing and exit with status code `1`.

if [ "$#" -eq 0 ]
then
    sed 's/^\(.\+\?\):\s*\(.*\)\s*$/"\1"\n"\2"/' | xargs -n 2 "$0"
    exit 0
elif [ "$#" -gt 2 ] || [ "$1" = "" ]
then
    exit 1
fi

inject_script="$(dirname "$0")/inject.awk"

echo '<meta name="{{name}}" content="{{content}}">' \
    | awk -f "$inject_script" "name" "$1" \
    | awk -f "$inject_script" "content" "$2"
