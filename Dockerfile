FROM eclipse-temurin:26-jdk

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        git \
        curl \
        vim \
        htop \
        sudo \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* && \
    curl -fsSL https://deb.nodesource.com/setup_22.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g pnpm

RUN echo ubuntu ALL=\(root\) NOPASSWD:ALL > /etc/sudoers.d/ubuntu \
    && chmod 0440 /etc/sudoers.d/ubuntu

RUN mkdir -p /var/www/javachat && chown -R ubuntu:ubuntu /var/www

WORKDIR /var/www/javachat/apps/backend

USER ubuntu

EXPOSE 8080

CMD ["sleep", "infinity"]
