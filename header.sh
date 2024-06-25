#!/usr/bin/env sh

list=$(find ./src -type f -name '*.java')

text=$(cat <<EOF
/*
 * Interdisciplinary Workshop B
 * Climate Monitoring
 * A.A. 2023-2024
 *
 * Authors:
 * - Iuri Antico, 753144, VA
 * - Beatrice Balzarini, 752257, VA
 * - Michael Bernasconi, 752259, VA
 * - Gabriele Borgia, 753262, VA
 *
 * Some rights reserved.
 * See LICENSE file for additional information.
 */
EOF
)

tmp=$(mktemp)

for file in $list
do
  [ "$(head -1 $file)" = "/*" ] && continue
  echo "$text" > $tmp
  cat "$file" >> $tmp
  cat "$tmp" > $file
done

rm "$tmp"