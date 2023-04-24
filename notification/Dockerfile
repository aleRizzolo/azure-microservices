FROM node:18

WORKDIR "/app"

COPY package* ./

RUN npm install

COPY dist dist/

ENV PORT=3000

EXPOSE ${PORT}

CMD [ "npm", "run", "start"]