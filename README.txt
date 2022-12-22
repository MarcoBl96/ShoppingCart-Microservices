Originales Projekt: https://github.com/ikismail/ShoppingCart

================================================
ShoppingCart-Microservices Änderungen:
================================================
-Spring Boot Implementierung
-Spring Security Konfiguration von XML auf JAVA
-Spring Flow Konfiguration von XML auf JAVA
-Web.xml entfernt
-Datenbank Umstellung von H2 auf MariaDB
-Migration auf Microservice Architektur mit REST Schnittstellen
-Discovery-Service + Loadbalancer implementiert
-Frontend von JSP auf AngularJS umgestellt mit eigenem API-Gateway Service
-Authentifizierung umgestellt auf Spring Webflux Security
-Dockerfile + Docker-Compose Config Hinzugefügt
================================================


================================================
Installation + Ausführung
================================================

1. Entwicklungsumgebung IntelliJ Idea Einrichten
	* [Download IntelliJ Idea](https://www.jetbrains.com/idea/download/#section=windows)

2. Build Tool - Maven
	* [Download Maven](https://maven.apache.org/download.cgi)

3. Datenbank - MariaDB
	* [Download MariaDB](https://mariadb.org/download/?t=mariadb&p=mariadb&r=10.8.3&os=windows&cpu=x86_64&pkg=msi&m=hs-esslingen)

4. Projekt in Intellij Idea öffnen
	* File -> Open -> ShoppingCart-Microservice

5. Datenbankverbindung in den jeweiligen Microservices anpassen
	* Open: src/main/resources/application.yml
	* Passe die Konfiguration an Installation an:
		* URL: 		spring.datasource.url=jdbc:mariadb://mariadb:3306/shoppingcart
		* Username:	spring.datasource.username=root
		* Passwort:	spring.datasource.password=password

6. Projekt in Intellij ausführen (Tipp: mehrere Terminal Fenster in Intellij öffnen)
	*discovery-service starten:
		* Command: "cd .\discovery-service\"
		* Command: "mvn spring-boot:run"
	*api-gateway starten:
		* Command: "cd .api-gateway\"
		* Command: "mvn spring-boot:run"
	*user-service starten:
		* Command: "cd .\user-service\"
		* Command: "mvn spring-boot:run"
	*product starten:
		* Command: "cd .\product-service\"
		* Command: "mvn spring-boot:run"
	*cart-service starten:
		* Command: "cd .\cart-service\"
		* Command: "mvn spring-boot:run"
	*order-service starten:
		* Command: "cd .\order-service\"
		* Command: "mvn spring-boot:run"
	* Öffne Browser und gehe zu: "http://localhost:8080/"
	* Fertig

7. Projekt mit Docker ausführen
	* Docker for Windows Installation
		* [Download Docker(https://docs.docker.com/desktop/install/windows-install/)

	* Docker Ausführen

	* Diese Schritt mit allen Services wiederholen:

		* Service mit Maven kompilieren:
			* Command: "mvn package spring-boot:repackage"

		* Docker Image Build mit Dockerfile 
			* Command: "docker build -t shoppingcart-microservice/product-service ."

	* Docker Compose ausführen (Config: docker-compose.yml)
		* Command: "docker-compose up"	
	 
	* Öffne Browser und gehe zu: "http://localhost:8080/"

8. Projekt in der Google Cloud in der Kubernetes Engine ausführen:
	* Gehe zu: "https://console.cloud.google.com/"
	* Mit Google Account einloggen (Kreditkarte benötigt für Google Cloud)
	* Projekt Erstellen
	* Alle Docker Images (inkl. MariaDB -> mariadb:10.7) taggen und in Google Cloud Container Registriy hochladen
		* Anleitung: https://cloud.google.com/container-registry/docs/pushing-and-pulling 

	* Neues GKE Standard Cluster in der Kubernetes Engine erstellen:
		* Region: europe-west3-c
		* Knoten: 5
		* Maschinentyp: e2-medium


	* Projekt auf dem Cluster ausführen:
		* CMD auf lokalen PC öffnen
		* Google Cloud CLI muss installiert sein
		* glcoud initialisierung mit Command "gcloud init"
		* Jeden Service inkl. mariadb anhand seiner Kubernetes Config (deployment.yaml) im Cluster einrichten:
			* mit CMD ins Verzeichnis vom Service wechseln "cd .\api-gateway"
			* kubectl apply -f deployment.yaml
		* Externen Loadbalancer mit externer IP in Google Webinterface für api-gateway einrichten
			* Arbeitslasten -> api-gateway -> Verfügbar machende Dienste -> Verfügbar machen -> Port 8080-8080
		* Webshop kann mit externer IP im Browser aufgerufen werden
		

