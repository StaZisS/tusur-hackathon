FROM node:18-alpine
WORKDIR /app

EXPOSE 5173

COPY ./web/package.json ./
RUN yarn install
COPY ./web ./
CMD ["yarn", "dev"]