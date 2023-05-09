#!/usr/bin/env bash

CURRENT_DIR=$(dirname $0)

[ -d ${CURRENT_DIR}/chattyai.tar.gz ] && rm ${CURRENT_DIR}/chattyai.tar.gz
cp ${CURRENT_DIR}/../target/chattyai.jar chattyai.jar
tar -zcvf ${CURRENT_DIR}/chattyai.tar.gz ${CURRENT_DIR}/static.conf ${CURRENT_DIR}/run.conf ${CURRENT_DIR}/run.sh ${CURRENT_DIR}/chattyai.jar
rm -rf ${CURRENT_DIR}/chattyai.jar