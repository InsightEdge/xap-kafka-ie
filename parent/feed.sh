#!/usr/bin/env bash

#export GIGA_12=$INSIGHTEDGE_HOME
export GIGA_12=$GS_HOME
export CLASSPATH="$GIGA_12/lib/required/*"
export CLASSPATH="$CLASSPATH:/code/xap-kafka-ie/parent/common/target/common-1.0.0-SNAPSHOT.jar:/code/xap-kafka-ie/parent/feeder/target/feeder-1.0.0-SNAPSHOT.jar"


java -Xms1g -Xmx1g -cp $CLASSPATH org.openspaces.pu.container.integrated.IntegratedProcessingUnitContainer