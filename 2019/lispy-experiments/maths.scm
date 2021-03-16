(module maths (sum)
  (import scheme)

  (define pi 3.14159265358979)

  (define (sum vs)
    (apply + vs)))

(module (maths average) (mean)
  (import scheme
          maths)

  (define (mean vs)
    (/ (sum vs)
       (length vs))))
