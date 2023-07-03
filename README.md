# KlashaCodeChallenge-How to run the application locally
Guide for running the project. Clone the repository from this link https://github.com/Ayodan/klashaCodeChallenge

Open project on any IDE prefarably intellij which runs on java jdk 11 and above

Allow maven to download and add all dependencies to classpath

Set up docker desk top locally and spin up a postgres conatainer (use the article "https://arctype.com/postgres/install/docker-windows-postgres" to learn to set up docker desktop and spin up a postgres container)

Connect to postgres database using client like dbeaver and create a database with name "klashadb"

Run the application locally with intellij

To access the Swagger UI for better understanding and testing, kindly open this URL "http://localhost:8080/swagger-ui.html#" when the app runs locally on port 8080

NOTE: If you do not have the postgres database container running and if you do not create the database on dbeaver or any any database client, the application will fail to start up due to database connection
