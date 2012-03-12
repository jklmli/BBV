PAPER = cs598

all: build

build:
	@@echo "Compiling LaTeX..."
	@@pdflatex $(PAPER).tex
	@@bibtex $(PAPER).aux
	# Need to execute again to add BibTeX extras
	@@pdflatex $(PAPER).tex

clean:
	@@echo "Removing LaTeX auto-gens..."
	@@rm -f *.aux *.bbl *.blg *.log *.pdf
