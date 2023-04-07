#!/bin/bash
set -e

query="SELECT hero_name FROM superhero;"
lookfor="Batman"

usage=$(cat <<USAGE

Usage: $(basename "$0") <defaults-file>
USAGE
)

summary=$(cat <<SUMMARY

The script is used to test the application after it has 
been deployed. It looks for known values first in the 
database using SQL  and the in web app using http.

The path to the defaults-file is a required parameter.

EXAMPLES:
  $(basename "$0") .my.dev.cnf
  $(basename "$0") .my.prod.cnf

The defaults-file should contain the following:

BOF>>>>>>>>>>
[client]
  user=<username>
  password=<password>
  database=<database>
  host=<host>
  port=<port>
<<<<<<<<<<EOF

Note: 
When host is 'localhost', the port is ignored. For development profile use 
127.0.0.1 or your hostname ('$(hostname)') instead.

For production settings the host should be the public IP address or CNAME
to test accessiblity from WAN side.

SUMMARY
)

if [ -z "$1" ]; then
  echo "Defaults-file argument missing." 
  echo "$usage"
  echo "$summary"
  exit 1
fi

if stat $1 >/dev/null 2>&1; then
  echo "Using defaults-file: $1"
else
  echo "Defaults-file: $1 not found"
  exit 1
fi

host=$(git config --file $1 client.host) # mysql config file has the same syntax as git config files

echo
echo Testing SELECT statement against database @ $host looking for $lookfor
echo
docker exec -i mysql-server mysql --defaults-file=$1  -e "$query" | grep $lookfor

echo
echo Testing frontend against web app @ $host looking for $lookfor
echo
curl --silent http://$host:8080/superhero/all2 | grep -e $lookfor  