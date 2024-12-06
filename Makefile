all: build

build:
	javac -d . Calculator.java
	javac Main.java