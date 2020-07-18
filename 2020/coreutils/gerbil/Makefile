GXE=gxc -exe -O

TOOLS=cat echo yes

%: %.ss
	$(GXE) -o $@ $<

all: $(TOOLS)

clean:
	rm $(TOOLS)

.PHONY: all clean
