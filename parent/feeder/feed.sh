#!/usr/bin/env bash

export GRID_DIR="$INSIGHTEDGE_HOME/datagrid"
export CLASSPATH="$GRID_DIR/lib/required/*"
export PROJECT_DIR="/code/xap-kafka-ie/parent"
export CLASSPATH="$CLASSPATH:$PROJECT_DIR/common/target/common-1.0.0-SNAPSHOT.jar:$PROJECT_DIR/feeder/target/feeder-1.0.0-SNAPSHOT.jar"

java -Xms1g -Xmx1g -cp $CLASSPATH org.openspaces.pu.container.integrated.IntegratedProcessingUnitContainer