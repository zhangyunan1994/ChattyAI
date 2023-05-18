#!/bin/sh

function help()
{
    echo "  usage:
    \t$0 start    ---   start service
    \t$0 stop     ---   stop service (with close resource and handler rest request)
    \t$0 restart  ---   restart service
    \t$0 kill     ---   kill service !!!(kill -9 $pid)
    "
}

function init()
{
    CURRENT_DIR=$(dirname $0)

    # project configuration, fetched from source code /bin direct
    source ${CURRENT_DIR}/run.conf
    # machine configuration, created by ops
    source ${CURRENT_DIR}/static.conf

    LOG_DIR=${CURRENT_DIR}/logs

    LOG_FILE=${LOG_DIR}/chattyai-center.log
    PID_FILE=${CURRENT_DIR}/chattyai.pid
    [ ! -d ${LOG_DIR} ] && mkdir -p ${LOG_DIR}
}

function start()
{
    echo "Start service [${PROJECT}]."
    nohup java \
      -Dcom.sun.management.jmxremote \
      -Dcom.sun.management.jmxremote.local.only=true \
      -Dcom.sun.management.jmxremote.authenticate=false \
      -Dcom.sun.management.jmxremote.ssl=false \
      ${JVMARGS} -jar \
      ${CURRENT_DIR}/${JAR_NAME} --spring.profiles.active=${SPRING_PROFILES} --server.port=${SERVER_PORT} >> ${LOG_FILE} 2>&1 &
    jobs | grep Running > /dev/null
    pid=$!
    if [ $? -eq 0 ];then
        echo ${pid} > ${PID_FILE}
        echo "Start service [${PROJECT}] done."
    else
        echo "Start service [${PROJECT}] failed!"
    fi
}

function stop()
{
    echo "Stop service [${PROJECT}], please waiting...."
    if [ -f ${PID_FILE} ];then
        /bin/kill -s SIGTERM `head -n 1 ${PID_FILE}`
        if [ $? -eq 0 ];then
            checkPid
            echo "Stop service [${PROJECT}] done."
            rm -f ${PID_FILE}
        else
            echo "Stop service [${PROJECT}] failed!"
        fi
    else
        echo "[${PROJECT}] no program running!"
    fi
}

function restart()
{
    stop
    start
}

function checkPid()
{
    if [ -f ${PID_FILE} ];then
        pid="$(head -n 1 ${PID_FILE})"
        i=0
        while [ $i -le 100 ];
        do
            line=`ps -p ${pid} | wc -l`
            if [ $line -le 1 ];then
                break
            fi
            sleep 1s
            echo "[${PROJECT}] program running! time = $i"
            i=`expr $i + 1`
            if [[ $i -eq 99 ]];then
                /bin/kill -9 `head -n 1 ${PID_FILE}`
            fi
        done
    else
        echo "[${PROJECT}] no program running!"
    fi
}

function kill()
{
    if [ -f ${PID_FILE} ];then
        pid="$(head -n 1 ${PID_FILE})"
        `kill -9 $pid`
        echo "[${PROJECT}] killed success!"
    else
        echo "[${PROJECT}] no program running!"
    fi
}

if [ $# -ne 1 ];then
    help
    exit 1
fi
action=$1
case $action in
        start)
                init;start
                ;;
        stop)
                init;stop
                ;;
        restart)
                init;restart
                ;;
        kill)
                init;kill
                ;;
        *)
                help
                ;;
esac