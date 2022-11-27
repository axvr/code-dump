# NAND S-expression Compiler

# a NAND b
(a b)

# a AND b
((a b) (a b))

# NOT a
(a a)

# a OR b
((a a) (b b))

# a NOR b
(((a a) (b b))
 ((a a) (b b)))

# x XOR y
((a (a b))
 (b (a b)))



((and1 and2) (and1 and2)) -> and0



((or1 or1) (or2 or2)) -> or0



(a b) -> a-xor-b
(a-xor-b c) -> s
((a-xor-b c) (a-xor-b c)) -> a-xor-b+and+c
((a b) (a b)) -> a-and-b
((a-and-b a-and-b) (a-xor-b+and+c a-xor-b+and+c)) -> c_out







((xor_a (xor_a xor_b))
 (xor_b (xor_a xor_b))) -> xor_o

a -> xor_a
b -> xor_b
xor_o -> xor_a
c -> xor_b





# Half adder

(((a b) a)
 ((a b) b)) -> s

((a a) (b b)) -> c


# Full adder

((((a (a b)) (b (a b)))
  (((a (a b))
    (b (a b))) c))
 (c (((a (a b))
      (b (a b))) c))) -> s

((((((a (a b))
     (b (a b))) c)
   (((a (a b))
     (b (a b))) c))
  ((((a (a b))
     (b (a b))) c)
   (((a (a b))
     (b (a b))) c)))
 (((a b) (a b))
  ((a b) (a b)))) -> c_out


# SR NAND latch (flip-flop)

_S -> 0
_R -> 1

(_S _Q) -> Q # (NOT S) NAND (NOT Q) = Q
(_R Q) -> _Q # (NOT R) NAND Q = (NOT Q)

(1 1) -> 0 (1)
(0 0) -> 1 (0)

(1 ?) -> _? (1) # If a NAND contains a 0, the output is always 1, thus resolving the "unknown dilemma" through a short-circuit.
(0 _?) -> ? (0)

(_S (_R (_S (_R ...)))) # Recursive. Allows it to maintain state.

# Bus syntax

1 -> addr0
0 -> addr1
1 -> addr2
1 -> addr3
1 -> addr4
0 -> addr5
0 -> addr6
1 -> addr7
0 -> addr8
1 -> addr9
1 -> addr10
1 -> addr11
0 -> addr12
0 -> addr13
0 -> addr14
0 -> addr15



# Using this parallel processing

(not_in not_in) -> not_out

(1 0) -> not_in



# Alternative syntax

# 1.

(def ...)
(nand ...)

(define not (a) (nand a a))

(define and (a b)
  (nand (nand a b)
        (nand a b)))

(define or (a b)
  (nand (nand a a)
        (nand b b)))

(define xor (a b)
  (nand (nand a (nand a b))
        (nand b (nand a b))))

(define nor (a b)
  (or (not a) (not b)))

(not a) -> not_a

# 2.

# When using this how to get value back out if a different one could be used?
# 2.1. Objects
# 2.2. Separate ones for different purposes with different names
# 2.3. Keep hold of reference

# 2.2.

_S -> 0
_R -> 1

(_S _Q) -> Q # (NOT S) NAND (NOT Q) = Q
(_R Q) -> _Q # (NOT R) NAND Q = (NOT Q)

(define _sr_nand_latch (_S _R)
  ((_S _Q) -> Q)
  ((_R Q) -> _Q))

(_sr_nand_latch 0 1)

# 2.1.

CELL:

   a  .  x
    \ . /
I    [#]    O
    / . \
   b  .  z

1 -> o.a
1 -> o.b

o.x -> 1
o.z -> 0

(cell add
  ((a b) -> (and a b) -> r)
  ((a b) -> (xor a b) -> c))


(cell latch
  (_S -> (nand _S _Q) -> Q)
  (_R -> (nand _R Q) -> _Q))

(cell latch
  (S -> (not S) -> _S) # Usability alias.
  (R -> (not R) -> _R) # Usability alias.
  (_S -> (nand _S _Q) -> Q)
  (_R -> (nand _R Q) -> _Q))

  # If only one value is given, an incomplete expression is returned. e.g. Expecting 'Q', 'R' or '_R'. 
  # ^ Pseudo-partial function application.
  # Return new copy of the object?
  # Aliases: (a -> b)

[[latch S:1 R:0] Q]

(latch l)
[l S:1 R:0]
[l Q]

(cell latch
  ((_S @_Q) -> (nand _S @_Q) -> @Q)
  ((_R @Q) -> (nand _R @Q) -> @_Q))

Cell: [ Input values --> Operation --> Output values ]

# Hold copy of cell
(hold l latch)
# Provide inputs
[l _S: 0 _R: 1]
# Retrieve result
(define result [l Q])
# Release cell
(free l)


# Merge Clojure-style functional programming with real OOP?
# Everything as an object?


[not x:1]
True Not. # Smalltalk
