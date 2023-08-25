
.PHONY: all run clean sim help

all:
	./gradlew build

clean:
	./gradlew clean

run:
	./gradlew run

sim:
	export FSW_ENV="SIM" && ./gradlew run

help:
	echo "make (all|clean|run|sim|help)"
