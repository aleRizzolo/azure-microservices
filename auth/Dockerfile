FROM node:18-alpine3.14

WORKDIR "/app"

COPY package* ./

RUN npm install

COPY dist dist/

ENV PORT=80

ENV USER_URL=http://user-service

EXPOSE ${PORT}

CMD ["npm", "run", "start"]