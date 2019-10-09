FILE=Report
HOME=$(shell pwd)/
OUT=${HOME}out/
RES=${HOME}res/

all: pdf html

pdf: setup
	pandoc -s -t latex --resource-path="${RES}" --highlight-style=tango "${HOME}${FILE}.md" "${HOME}metadata.yaml" -o "${OUT}${FILE}.pdf"

html: setup
	pandoc -s -t html5 --katex --resource-path="${RES}" --highlight-style=tango "${HOME}${FILE}.md" "${HOME}metadata.yaml" -o "${OUT}${FILE}.html"

setup:
	mkdir -p "${OUT}"

clean:
	rm -rf "${OUT}"
