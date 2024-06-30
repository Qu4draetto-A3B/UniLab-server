# Main Class, project name, change as preferred
MAIN_CLASS := org.a3b.serverCM.ServerCM
VERSION := 1.0
PRJ_NAME := UniLab-server
EXE_NAME := UniLab-server-$(VERSION)

# Directories for where to find build files, libraries and sources, respectively
BUILD_DIR := ./target
LIB_DIR := ./lib
SRC_DIR := ./src
JAVADOC_DIR := ./javadoc

# Manual files
MAN_DIR := $(DOC_DIR)/man
MAN_USER := Manuale_Utente.pdf
MAN_TECH := Manuale_Tecnico.pdf
MAN_USER_OUT := $(MAN_DIR)/out/$(MAN_USER)
MAN_TECH_OUT := $(MAN_DIR)/out/$(MAN_TECH)

# Names for generated files
TARGET_JAR := $(BUILD_DIR)/$(EXE_NAME)-shaded.jar
TARGET_EXE := $(BUILD_DIR)/$(EXE_NAME)
TARGET_WIN := $(BUILD_DIR)/$(EXE_NAME).exe
TARGET_DIR := $(PRJ_NAME)
TARGET_ZIP := $(BUILD_DIR)/$(TARGET_DIR).zip

SRCS := $(shell find ./src -type f -name '*.java')

# Compile and run project
run: jar
	java -jar $(TARGET_JAR)

# Compile sources to classes
classes:
	mvn compile

# Generate .jar artifact
jar: classes
	mvn package

# Package jar into an executable
exe_linux: jar
	echo '#!/usr/bin/java -jar' > $(TARGET_EXE)
	cat $(TARGET_JAR) >> $(TARGET_EXE)
	chmod +x $(TARGET_EXE)

exe_win: jar
	java -Djava.awt.headless=true -jar ./launch4j/launch4j.jar ./launch4j/jar2exe.xml

# Generate documentation
docs: $(SRCS)
	mvn site
	mvn javadoc:javadoc

# Clean BUILD_DIR by deleting it
clean:
	mvn clean

# Delete documentation
cleandoc:
	rm -r $(JAVADOC_DIR)

all: classes jar docs exe_linux exe_win package

cleanall: clean cleandoc

.PHONY: classes run jar clean docs cleandoc libraries package
