#!/bin/bash

read -p "Insert your regitry name: " registry_name

auth_image_name=$registry_name/auth
notification_image_name=$registry_name/notification
user_image_name=$registry_name/user

echo "*** Building auth ***"
cd ./auth
npm install && npm run build
docker build -t $auth_image_name .

clear
echo "*** Building notification ***"
cd ../notification
npm install && npm run build
docker build -t $notification_image_name . 

clear
echo "*** Building user ***"
cd ../user
npm install && npm run build
docker build -t $user_image_name  .

clear
echo "*** Pushing auth ***"
docker push $auth_image_name

clear
echo "*** Pushing notification ***"
docker push $notification_image_name

clear
echo "*** Pushing user ***"
docker push $user_image_name