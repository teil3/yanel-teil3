#!/bin/sh

echo "Start/Stop Yanel ..."

MAVEN_REPO_LOCAL=~/.m2/repository

java -classpath build/classes:lib:src/resources/tape/build/classes:src/resources/invoice/build/classes:src/resources/websearch/build/classes:$MAVEN_REPO_LOCAL/yarep/yarep/0.0.1-dev-r13828/yarep-0.0.1-dev-r13828.jar:$MAVEN_REPO_LOCAL/log4j/log4j/1.2.8/log4j-1.2.8.jar:$MAVEN_REPO_LOCAL/avalon-framework/avalon-framework-api/4.1.5/avalon-framework-api-4.1.5.jar:$MAVEN_REPO_LOCAL/avalon-framework/avalon-framework-impl/4.1.5/avalon-framework-impl-4.1.5.jar:$MAVEM_REPO_LOCAL/apache-jakarta-commons/apache-jakarta-commons-discovery/0.2/apache-jakarta-commons-discovery-0.2.jar:$MAVEN_REPO_LOCAL/apache-jakarta-commons/apache-jakarta-commons-id/0.1-dev-lcr357257/apache-jakarta-commons-id-0.1-dev-lcr357257.jar:$MAVEN_REPO_LOCAL/apache-jakarta-commons/apache-jakarta-commons-logging/1.0.4/apache-jakarta-commons-logging-1.0.4.jar org.wyona.yanel.cmdl.YanelCommandLine
