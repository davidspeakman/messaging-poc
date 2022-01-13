#!/bin/bash

keytool -genkey -alias broker -keyalg RSA -keystore broker.ks -storepass XXXXXX -dname CN=$AMQ_MQTT_ROUTE_HOST
keytool -export -alias broker -keystore broker.ks -file broker_cert -storepass XXXXXX 
keytool -genkey -alias client -keyalg RSA -keystore client.ks -storepass XXXXXX -dname CN=localhost
keytool -import -alias broker -keystore client.ts -file broker_cert -storepass XXXXXX -noprompt
oc create secret generic amq-mqtt-mqtt-secret \
--from-file=broker.ks=broker.ks \
--from-file=client.ts=broker.ks \
--from-literal=keyStorePassword=XXXXXX \
--from-literal=trustStorePassword=XXXXXX \
 -n amq-mqtt

 oc secrets link sa/amq-broker-operator secret/amq-mqtt-mqtt-secret -n amq-mqtt
