PAPER = cs598

.PHONY: doc

doc:
	@@make -C src/doc build
	@@mv src/doc/$(PAPER).pdf doc/
	@@make -C src/doc clean

clean:
	@@echo "Removing docs..."
	@@rm -f doc/*

