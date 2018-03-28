#!/bin/sh

# Easily install a NuGet package locally on UNIX-like systems


# Print usage information
usage() {
    echo "usage: local-nupkg [-p package] [-v version] [-n name] [-u]"
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
        sed -n 's/^.\+\.\([0-9]\+\.[0-9]\.[0-9]\)\.nupkg$/\1/gp')
fi

if [ -z "$PACKAGE_NAME" ]
then
    PACKAGE_NAME=$(basename "$PACKAGE" | \
        sed -n 's/\(\.[0-9]\+\.[0-9]\+\.[0-9]\+\)\?\.nupkg$//gp')
fi

if [ -z "$PACKAGE" -o -z "$VERSION" ]
then
    usage
fi

if [ ! -f "$PACKAGE" ]
then
    echo "Error: Package: \"${PACKAGE}\" does not exist"
    exit 1
fi


NUGET_PATH="${HOME}/.nuget/packages/${PACKAGE_NAME}/${VERSION}"

mkdir -p "$NUGET_PATH"
mv "$PACKAGE" "$NUGET_PATH"
echo "Installed to: ${NUGET_PATH}"

exit 0

