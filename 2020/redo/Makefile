all: redo redo-ifchange

redo: redo-rc.o redo.o
	csc redo.o redo-rc.o -o redo

redo.o: redo.scm
	csc -c redo.scm

redo-ifchange: redo-rc.o redo-ifchange.o
	csc redo-ifchange.o redo-rc.o -o redo-ifchange

redo-ifchange.o: redo-ifchange.scm
	csc -c redo-ifchange.scm

redo-rc.o redo-rc.import.scm: redo-rc.scm
	csc -c -j redo-rc redo-rc.scm

clean:
	rm -f redo redo.o \
		redo-rc.o redo-rc.import.scm \
		redo-ifchange redo-ifchange.o

.PHONY: all clean
