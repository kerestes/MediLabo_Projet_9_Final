FROM node:alpine AS builder
RUN mkdir -p /app
WORKDIR /app
COPY . .

EXPOSE 4200

RUN npm install -g @angular/cli
RUN npm install

CMD ["ng", "serve", "--host", "0.0.0.0"]
