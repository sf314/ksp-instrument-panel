
.PHONY: all run clean sim

all:
	./gradlew build

clean:
	./gradlew clean

run:
	./gradlew run

sim:
	export FSW_ENV="SIM" && ./gradlew run
