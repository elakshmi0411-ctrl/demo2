FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

# Install Chrome dependencies and Chromium
RUN apt-get update && apt-get install -y chromium \
    && rm -rf /var/lib/apt/lists/*

# Copy Maven project
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy remaining project files
COPY . .

# Set Chrome binary
ENV CHROME_BIN=/usr/bin/chromium

# Run TestNG tests
CMD ["mvn", "clean", "test"]