#! bin/bash
cat $1 | tr '[:upper:]' '[:lower:]'|sed 's/\s/\n/g'  | sort | uniq -c | sort -n > $2
 