sdk install java 17.0.10-amzn

mvn install 

mvn spring-boot:run

cd /workspace/Group1-Project/WebEngBigProject/src/main/react && npm run build && cd /workspace/Group1-Project/WebEngBigProject/ && mvn spring-boot:run