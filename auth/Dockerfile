FROM node:18-alpine3.14

WORKDIR "/app"

COPY package* ./

RUN npm install

COPY dist dist/

ENV PORT=3000

EXPOSE 3000

CMD [ "npm", "run", "start"]