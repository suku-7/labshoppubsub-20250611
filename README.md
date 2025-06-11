# Model
# lab-shop-pub-sub (circuit breaker 적용 테스트)
## 고의로 처리 속도를 늦추고, circuit breaker를 통해서 일정 시간초를 초과하면 에러 발생 / 500에러가 발생할 경우 fallback 처리
https://labs.msaez.io/#/189596125/storming/labshop-monolith-2

![스크린샷 2025-06-11 151258](https://github.com/user-attachments/assets/b1c4ed7c-c85a-4adb-b845-206eac3931c4)
![스크린샷 2025-06-11 155220](https://github.com/user-attachments/assets/c9df1872-a921-4127-ab64-bbc68a08f39f)
![스크린샷 2025-06-11 155344](https://github.com/user-attachments/assets/fe2b48f3-5556-4b96-872a-a9417dff57a0)
![스크린샷 2025-06-11 155351](https://github.com/user-attachments/assets/b38b4a49-32d9-46cd-8a47-65147a16a73e)
![스크린샷 2025-06-11 155631](https://github.com/user-attachments/assets/b2dab4d0-4b5b-4151-9554-36dbef4a0f7f)
![스크린샷 2025-06-11 155909](https://github.com/user-attachments/assets/81c68324-7f7c-43be-ae9b-c2aa71f57c70)
![스크린샷 2025-06-11 155914](https://github.com/user-attachments/assets/2ec3875d-6a8a-4fe9-a984-ffc698d822d4)
![스크린샷 2025-06-11 160335](https://github.com/user-attachments/assets/d822266c-9d00-4d16-8de4-cd96add3bdd7)
![스크린샷 2025-06-11 160459](https://github.com/user-attachments/assets/ee96fccc-61aa-41ab-a2a8-f6b04c13e818)

## 터미널 작성 참고용
0. 재실행시
- java -version
- ./init.sh
- cd kafka
- docker-compose up -d (9092 포트 실행이 안되었으면)
1. sdk install java

2. lombok 1.18.30으로 수정
- order, inventory 버전 수정

3. order 실행
- cd order/
- mvn spring-boot:run

4. inventory 실행
- cd inventory/
- mvn spring-boot:run

5. 재고를 넣고 시즈로 빼내기
- http :8082/inventories id=1 stock=10000
- siege -c2 -t10S  -v --content-type "application/json" 'http://localhost:8081/orders POST {"customerId":1001, "customerId":1001, "productId":1, "qty":1}'
- req/res를 하면은 장애가 전파되는 문제가 발생하게 된다.

6. 고의로 폭탄 생성
    @PostLoad
    public void makeDelay(){
        try {
            Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

7. circuit breaker enable 및 주석 제거
- application.yml 수정

다시 테스트
- 500에러 발생하면서 끊어버림
- 여러분들이 몇초가 걸릴지를 알고 있기 때문에 왜 끊어야되는데 라는 생각 10초, 20초 그 이상일지 모르니까
 한도 끝도 없이 기다릴 수 없다.

8. 인벤토리 끄고 시즈 실행
- 모두 order를 처리할 수 없는 상태가 된다.

9. fallback을 생성
- inventory를 재고 안채우고서 시즈 넣어보니까 모두 다 처리가 되네.
- 안전재고 100개는 위험한거 같고 1~10개정도는 충분히 처리 되니까 


## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- order
- inventory


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- order
```
 http :8088/orders id="id"productId="productId"qty="qty"customerId="customerId"amount="amount"status="status"address="address"
```
- inventory
```
 http :8088/inventories id="id"stock="stock"
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
