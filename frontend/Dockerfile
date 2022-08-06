# STAGE 1: Build app
FROM node:16.13.2-alpine as builder

WORKDIR /usr/local/app

COPY package*.json ./

RUN npm install

COPY . .

RUN $(npm bin)/ng build --configuration=production --source-map=false

# STAGE 2: Setup Server
FROM nginx:1.21.5-alpine

COPY --from=builder /usr/local/app/dist/frontend /usr/share/nginx/html

EXPOSE 80

CMD nginx -g "daemon off;"