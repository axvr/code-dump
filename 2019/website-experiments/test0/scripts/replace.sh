#!/bin/sh

# Attempt to replace all possible tags from a set of tags.

file=`cat`

IFS=$'\n'

dir="$(dirname "$0")"

for tag in $1
do
    key="$(echo "$tag" | awk 'BEGIN { FS=":" } { print $1 }')"
    val="$(echo "$tag" | awk -f "$dir/getvalue.awk" "$key")"
    file="$(echo "$file" | awk -f "$dir/inject.awk" "$key" "$val")"
done

echo "$file"
