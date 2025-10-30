# Use an official Node.js runtime as the base image
FROM node:18-alpine

# Set working directory
WORKDIR /usr/src/app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Copy all other source code
COPY . .

# Expose the port your app runs on (usually 3000 or 8080)
EXPOSE 3000

# Start the app
CMD ["node", "server.js"]
