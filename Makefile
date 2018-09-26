all: clean build

build:
	javac src/jb/*/*

run:
	@cd src && java jb.main.Game

clean:
	rm -f src/jb/*/*.class


