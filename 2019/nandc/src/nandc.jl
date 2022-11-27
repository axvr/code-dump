#!/usr/bin/env julia

# TODO: massively clean up
# TODO: generate the NAND tree

if length(ARGS) == 1
    path = ARGS[1]
    # TODO: check file actually exists.
else
    path = "ex2.nc"
end

contents = open(path, "r") do fh
    read(fh, String)
end

tokentypes = [
    [ :openparen,  x -> if x[1] == '('; '('; end ],
    [ :closeparen, x -> if x[1] == ')'; ')'; end ],
    [ :boolean,    x -> x[1] == '0' ? false : x[1] == '1' ? true : nothing ],
    [ :label,
        function (x)
            v = match(r"^[^01\s()][^\s()]*", x)
            if v != nothing
                v.match
            end
        end ]
]

function tokenise(stream)
    tokens = []

    stream = strip(stream)

    while length(stream) > 0
        for (type, rule) in tokentypes
            c = rule(stream)

            if c == nothing
                continue
            end

            push!(tokens, [type, c])

            stream = lstrip(stream[length(c)+1:end])

            break
        end
    end

    return tokens
end

#= tokenise(contents) =#
