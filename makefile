#javac src/ie/tcd/gourleys/Main.java src/ie/tcd/gourleys/system/*.java src/ie/tcd/gourleys/ui/*.java src/ie/tcd/gourleys/ui/listener/*.java -d bin/

JAVAC = javac
JVM = java
JAVADOC = javadoc
MKBIN = mkdir -p $(BIN)
BIN = bin/

JAVAC_FLAGS = -g -d $(BIN)
JAVAC_CP = -cp

JUNIT = /usr/share/java/junit.jar
HAMCREST = /usr/share/java/hamcrest-core.jar
RUNNER = org.junit.runner.JUnitCore

MAINSRC = src/ie/tcd/gourleys/
TESTSRC = src/test/
MAINFILES = \
	Main.java \
	ui/*.java \
	system/*.java
TESTFILES = \

TARGET = ie.tcd.gourleys.Main


.SUFFIXES : .class .java

all:
	$(MKBIN)
	$(JAVAC) $(JAVAC_FLAGS) $(addprefix $(MAINSRC),$(MAINFILES))

test:
	$(JAVAC) $(JAVAC_FLAGS) $(JAVAC_CP) $(JUNIT) $(MAINSRC)$(MAINFILES) $(TESTSRC)$(TESTFILES)

clean:
	$(RM) -r $(BIN)

run:
	$(JVM) $(JAVAC_CP) $(BIN) $(TARGET)

run_test: 
	$(JVM) $(JAVAC_CP) $(JUNIT):$(HAMCREST):$(TARGET) $(RUNNER) $(patsubst %.java,%,$(TESTFILES))
