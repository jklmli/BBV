DOCDIR = doc/

.PHONY: doc

doc:
	@@make -C $(DOCDIR) build
	@@make -C $(DOCDIR) clean

clean:
	@@echo "Removing generated cs598.pdf in docs..."
	@@rm -f doc/out/*

