#!/bin/sh

# Update spec files.

for spec in $(find . -type f -name '*.spec'); do
    dir="$(dirname "$spec")"
    pkg="$(basename "${spec%.spec}")"
    version_file="${dir}/${pkg}.version"
    if [ -f "$version_file" ]; then
        v="$($version_file)"
        cur_v="$(sed -ne 's/^Version:[[:space:]]\+\(.*\)$/\1/p' "$spec")"
        # Only update tags if the version has changed.
        if ! [ "$v" = "$cur_v" ]; then
            echo "$pkg: $cur_v --> $v"
            sed -e "s/^Version: .*$/Version: ${v}/" \
                -e 's/^Release: .*$/Release: 1%{?dist}/' \
                -i "$spec"
        fi
    fi
done
