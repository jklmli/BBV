DOCDIR = doc/

.PHONY: doc

doc:
	@@make -C $(DOCDIR) build
	@@make -C $(DOCDIR) clean

clean:
	@@echo "Removing docs..."
	@@rm -f doc/*

