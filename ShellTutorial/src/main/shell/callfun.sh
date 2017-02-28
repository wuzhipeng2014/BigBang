#!/bin/bash

. fun.sh

sums 3 4

res=$(sums 6 7)

echo $res

orderinput=result/search/vacation/log/order_join_hotdog/$res/*.gz

echo $orderinput