#! bin/bash

cat text/56667-0.txt| tr '[:upper:]' '[:lower:]'|sed 's/\s/\n/g'  | sort | uniq -c | sort -n
 