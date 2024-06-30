;;; Integer
10
20/2
#xa ; #XA #xA #Xa
+10
;;; All = 10.

;;; Ratios
1/10
+2/3
-1/3

#b101
#o777

#B1010/1011

123.0 ; 123.0
123.  ; 123

;; Custom radix.
#2r0101  ; binary again.
#16rABCD ; hex again.
#36rABCDEFGHIJKLMNOPQRSTUVWXYZ  ; base 36.


;;; Floats
1.0  ; 1.0
1e0  ; 1.0
1d0  ; 1.0d0
123.0  ; 123.0
123e0  ; 123.0
0.123  ; 0.123
.123   ; 0.123
123e-3 ; 0.123
123E-3 ; 0.123
0.123e20 ; 1.23e19 (reduced/normalised)
123d23 ; 1.23d25
;; Only base-10 works.  #b #x #o #r only work on rational numbers.

;; s - short float
;; f - single float
;; d - double float
;; l - long float
;; e - default (initially single float)


;;; Complex
#c(2 1)  ; #c(real zero-imaginary)
#c(2/3 3/4)
#c(2 1.0)  ; #c(2.0 1.0)
#c(2.0 1.0d0) ; #c(2.0d0 1.0d0)
#c(1/2 1.0) ; #c(0.5 1.0)
#c(3 0) ; 3
#c(3.0 0.0) ; #c(3.0 0.0)
#c(1/2 0) ; 1/2
#c(-6/3 0) ; -2
