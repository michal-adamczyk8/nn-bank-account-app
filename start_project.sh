mvn clean install

docker build -t nn-bank-account-app .

docker run -p 8089:8089 nn-bank-account-app