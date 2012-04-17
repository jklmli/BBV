PAPER = cs598

all: build

build:
	@@echo "Compiling LaTeX..."
	@@pdflatex $(PAPER).tex
	@@bibtex $(PAPER).aux
	# Need to execute again to add BiBTeX bibliography 
	@@pdflatex $(PAPER).tex
	# Need to execute yet again to replace citations
	@@pdflatex $(PAPER).tex
	@@mkdir -p out/
	@@mv $(PAPER).pdf out/

clean:
	@@echo "Removing LaTeX auto-gens..."
	@@rm -f *.aux *.bbl *.blg *.log *.pdf
