#!/bin/sh

# Easily install a NuGet package locally on UNIX-like systems
# Written by Alex Vear in 2018-03
# Public domain.  No rights reserved.


# Print usage information
usage() {
    printf "usage: local-nupkg [-p package] [-v version] [-n name] [-u]\n"
    exit 1
}


while getopts p:v:n:u o "$@"
do
    case "$o" in
        p)      PACKAGE="$OPTARG";;
        v)      VERSION="$OPTARG";;
        n)      PACKAGE_NAME="$OPTARG";;
        u)      usage;;
        [?])    usage;;
    esac
done


if [ -z "$VERSION" ]
then
    VERSION=$(basename "$PACKAGE" | \
        sed 's/^.\+\.\([0-9]\+\.[0-9]\.[0-9]\)\.nupkg$/\1/g')
fi

if [ -z "$PACKAGE_NAME" ]
then
    PACKAGE_NAME=$(basename "$PACKAGE" | \
        sed 's/\(\.[0-9]\+\.[0-9]\+\.[0-9]\+\)\?\.nupkg$//g')
fi

if [ -z "$PACKAGE" -o -z "$VERSION" ]
then
    usage
fi

if [ ! -f "$PACKAGE" ]
then
    printf "Error: Package: \"${PACKAGE}\" does not exist\n"
    exit 1
fi


NUGET_PATH="${HOME}/.nuget/packages/${PACKAGE_NAME}/${VERSION}"

mkdir -p "$NUGET_PATH"
mv "$PACKAGE" "$NUGET_PATH"
printf "Installed to: ${NUGET_PATH}\n"

exit 0

