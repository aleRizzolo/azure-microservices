#!/bin/bash

read -p "Insert your regitry name: " registry_name
image_name=$registry_name/auth

echo "*** Generating JavaScript ***"
npm install
npm run build

echo "*** Generating and pushing Docker image ***"
docker build -t $image_name .
docker push $image_name