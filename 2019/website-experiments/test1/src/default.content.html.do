#!/bin/sh

file="$(dirname "$1")/$2.md"

if [ -f "$file" ]; then
    redo-ifchange "$file"
    pandoc -f gfm -t html "$file"
fi
