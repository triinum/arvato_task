# arvato_task

Automated API and UI tests

## Running locally

Make sure you have DOCKER installed and running on your machine

```sh
git clone https://github.com/gulkhara/arvato_task.git # or download zip from github and unzip
cd arvato_task
```

### Environmental variables must be added before running tests

Open "docker-compose.yml" and change following variables:

```yml
- BASE_URI=changeme Ex:https://ap-ac-afterpay-api.azurewebsites.net/api/v3/validate/bank-account
- AUTH_TOKEN=changeme "X-Auth-Key token"
- BANK_ACCOUNT=changeme "Valid IBAN"
```


### Run containers

```sh
docker-compose up --build
```
After tests are finished, Allure report is generated. Report can be found by following link:
http://localhost:5252/allure-docker-service-ui/projects/default
