#!/bin/sh

root='..'
scripts="$root/scripts"

file="$(dirname "$1")/$2.content.html"

redo-ifchange $scripts/metatags.awk $scripts/genmetatag.sh \
              $scripts/replace.sh   $scripts/getvalue.awk \
              $scripts/inject.awk   $root/template.html

md_file="$(dirname "$1")/$2.md"
if [ -e "$md_file" ]; then
    redo-ifchange "$file" "$md_file"
else
    redo-ifchange "$file"
fi

tags="$(awk -f "$scripts/metatags.awk" "$file")"
meta="$(echo "$tags" | "$scripts/genmetatag.sh")"
content="$(cat "$file" | "$scripts/replace.sh" "$tags")"

title="$(echo "$tags" | awk -f "$scripts/getvalue.awk" 'title')"
[ -n "$title" ] && title="$title | Alex Vear" || title='Alex Vear'

cat "$root/template.html" \
    | awk -f "$scripts/inject.awk" 'title' "$title" \
    | awk -f "$scripts/inject.awk" 'meta' "$meta"   \
    | awk -f "$scripts/inject.awk" 'content' "$content"
