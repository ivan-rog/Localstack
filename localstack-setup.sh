#!/bin/sh
echo "Initializing localstack s3"

awslocal sqs create-queue --queue-name localstack-queue
awslocal s3api create-bucket --bucket localstack-s3