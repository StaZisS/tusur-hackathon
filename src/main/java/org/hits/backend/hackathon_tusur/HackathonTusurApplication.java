package org.hits.backend.hackathon_tusur;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication
public class HackathonTusurApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HackathonTusurApplication.class)
                .beanNameGenerator(new FullyQualifiedAnnotationBeanNameGenerator())
                .run(args);
    }

}

/*
https://stackoverflow.com/questions/38337895/globally-disable-https-keycloak
  keycloak:
    image: quay.io/keycloak/keycloak:24.0.2
    env_file:
      - .env
    ports:
      - '8082:8080'
    command: [ "start-dev", "--import-realm", "--log-level=ERROR"]
    volumes:
      - ./config/keycloak/import/realm.json:/opt/keycloak/data/import/realm.json:ro
    environment:
      - KEYCLOAK_ADMIN=${KEYCLOAK_ADMIN}
      - KEYCLOAK_ADMIN_PASSWORD=${KEYCLOAK_PASSWORD}
      - KC_HOSTNAME_STRICT=true
      - KC_HTTP_ENABLED=true
      - KC_HTTPS_ENABLED=false
      - KC_HOSTNAME=51.250.30.109
    networks:
      - app

docker exec -it 25c09dcefcad bash
cd /opt/keycloak/bin/
./kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin
./kcadm.sh update realms/master -s sslRequired=NONE
./kcadm.sh update realms/hits-project -s sslRequired=NONE
*/