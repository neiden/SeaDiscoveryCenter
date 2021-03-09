#!/bin/bash

# These lines terminate sub processes when
# this process is killed.
trap "exit" INT TERM ERR
trap "kill 0" EXIT

# How often to refresh data.
updateFrequency=10


# Start /datacollector/main.py in subprocess
(
cd ../datacollector
python3 main.py $updateFrequency &>/dev/null
) &


while [ 1 ]; do
	webdata=`python3 ./format_data.py`

	match='# Place tbody here'
	file='/var/www/html/index.html'

	cp ./index_clean.html $file
	sed -i "s@^$match.*@$webdata@g" $file

	time=`date`
	printf "[ $time ] Data refreshed.\n"
	systemctl stop apache2
	systemctl start apache2
	sleep $updateFrequency
done
