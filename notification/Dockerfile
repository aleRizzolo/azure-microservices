FROM node:18-alpine3.14

WORKDIR "/app"

COPY package* ./

RUN npm install

COPY dist dist/

ENV PORT=3000

ENV CONNECTION_STRING=Endpoint=sb://docker-test.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=LGvKN3JRB+bAtcnTWpRWcjz8WgCpaZvH9+ASbDitSC8=
ENV QUEUE_NAME=test-coda

EXPOSE ${PORT}

CMD [ "npm", "run", "start"]