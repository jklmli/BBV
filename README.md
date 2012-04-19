## Building

###Linux

#### Source files - cleaning, compiling, etc.
This project uses `sbt` (Scala Build Tool), refer to [this](https://github.com/harrah/xsbt/wiki/Getting-Started-Running).

#### Generating docs/paper
You'll need `pdflatex` and `bibtex` packages.  Afterwards, run `make doc` from the root of the repo.
The PDF will show up in `doc/out/`, assuming there are no errors.
