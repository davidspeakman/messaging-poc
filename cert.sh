#!/bin/bash

keytool -genkey -alias broker -keyalg RSA -keystore broker.ks -storepass aropoc -dname CN=amq-mqtt-mqtt-0-svc-rte-amq-mqtt.apps.r7dmod0x.canadaeast.aroapp.io
keytool -export -alias broker -keystore broker.ks -file broker_cert -storepass aropoc 
keytool -genkey -alias client -keyalg RSA -keystore client.ks -storepass aropoc -dname CN=localhost
keytool -import -alias broker -keystore client.ts -file broker_cert -storepass aropoc -noprompt
oc create secret generic amq-mqtt-mqtt-secret \
--from-file=broker.ks=broker.ks \
--from-file=client.ts=broker.ks \
--from-literal=keyStorePassword=aropoc \
--from-literal=trustStorePassword=aropoc \
 -n amq-mqtt

 oc secrets link sa/amq-broker-operator secret/amq-mqtt-mqtt-secret -n amq-mqtt
