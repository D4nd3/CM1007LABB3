FROM node:20 AS build

WORKDIR /app
COPY package.json package-lock.json tsconfig.json ./
RUN npm install
COPY . ./
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build /app/build /frontend-build
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]