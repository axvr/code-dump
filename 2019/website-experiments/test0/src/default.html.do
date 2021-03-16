#!/bin/sh

root='..'
scripts="$root/scripts"

# file="$(echo "$1" | sed 's/\.html$/.meta/')"
file="$(dirname "$1")/$2.meta"

redo-ifchange $scripts/metatags.awk $scripts/genmetatag.sh \
              $scripts/replace.sh   $scripts/getvalue.awk \
              $scripts/inject.awk   $root/template.html

redo-ifchange "$file"

# TODO: name of file
# TODO: check if the file contains Markdown and convert it using Pandoc.

tags="$(awk -f "$scripts/metatags.awk" "$file")"
meta="$(echo "$tags" | "$scripts/genmetatag.sh")"
content="$(cat "$file" | "$scripts/replace.sh" "$tags")"

title="$(echo "$tags" | awk -f "$scripts/getvalue.awk" 'title')"
[ -n "$title" ] && title="$title | Alex Vear" || title='Alex Vear'

cat "$root/template.html" \
    | awk -f "$scripts/inject.awk" 'title' "$title" \
    | awk -f "$scripts/inject.awk" 'meta' "$meta"   \
    | awk -f "$scripts/inject.awk" 'content' "$content"
