FROM node:18-alpine3.14

WORKDIR "/app"

COPY package* ./

RUN npm install

COPY dist dist/

ENV PORT=80

EXPOSE ${PORT}

CMD ["npm", "run", "start"]