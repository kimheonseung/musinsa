#!/bin/bash
docker build -t musinsa/backoffice:1.0 -f backoffice/Dockerfile backoffice
docker build -t musinsa/search:1.0 -f search/Dockerfile search
docker build -t musinsa/aggregator:1.0 -f aggregator/Dockerfile aggregator
docker build -t musinsa/gateway:1.0 -f gateway/Dockerfile gateway
docker-compose up -d
