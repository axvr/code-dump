all: cat true yes echo

cat: cat.scm
	csc -O5 -o cat cat.scm

true: true.scm
	csc -O5 -o true true.scm

yes: yes.scm
	csc -O5 -o yes yes.scm

echo: echo.scm
	csc -O5 -o echo echo.scm

clean:
	rm -f cat true yes echo

.PHONY: all clean
