DOCDIR = src/doc/
PAPER = cs598

.PHONY: doc

doc:
	@@make -C $(DOCDIR) build
	@@mv $(DOCDIR)$(PAPER).pdf doc/
	@@make -C $(DOCDIR) clean

clean:
	@@echo "Removing docs..."
	@@rm -f doc/*

