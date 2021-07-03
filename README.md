# Airline

Проект для автоматизации работы авиакомпании.

## Начало работы

### Сборка проекта локально

Для сборки проекта запустите скрипт buildImage.sh (в корне проекта).  
Собирается докер-образ 342011919275.dkr.ecr.us-east-2.amazonaws.com/airline:latest.  
Запустите докер-образ локально.   

    docker run -p 8080:8080 342011919275.dkr.ecr.us-east-2.amazonaws.com/airline:latest.

### Проверка запуска приложения

    http://localhost:8080
    
Если всё успешно собралось, приложение выводит строку "Hello, world!".    

## Deployment

Используется модель ветвления GitHub Flow.   
При коммите изменений в главную ветку (main) GitHub-репозитория CI-сервер GitHub Actions запускает сборку проекта, 
проверки кода и сборку докер-образа с номером коммита в качестве тэга.  
Докер-образ помещается в репозиторий Amazon Elastic Container Registry (ECR).  
Название репозитория 342011919275.dkr.ecr.us-east-2.amazonaws.com/airline.  
Production стенд размещается на Amazon Elastic Container Service Fargate (ECS).  
При появлении нового докер-образа в репозитории ECR создаётся новый инстанс приложения в ECS.  
Ему назначается динамический IP. Посмотреть новый IP можно в списке задач кластера ECS.
Кластер arn:aws:ecs:us-east-2:342011919275:cluster/airlineCluster.

### Проверка запуска приложения на production стенде

    http://<latest ECS IP>:8080
    
Если всё успешно собралось, приложение выводит строку "Hello, world!".    

## Проверки кода
Для проверки кода используется статический анализатор detekt.      
Проект не должен содержать warning'ов.  
Покрытие тестов должно быть не меньше 80%.  
Любой билд на главной ветке не должен содержать RC или SNAPSHOT-зависимости.    

Билд падает если условия проверок не выполнены.

# Вопросы к преподавателям!!!
Постаралась разобраться во всех пунктах Требований к проекту. Но по двум пунктам не поняла - нужно ли что-то дополнительно добавлять.  
Пункт 4 - Проект не должен содержать нарушений code-style. Для проверки code-style достаточно использовать detekt? Или должен быть какой-то отдельный конфиг в файле с настройками проверок для идеи?    
Пункт 7 - Разработчик должен легко понимать, почему упала сборка (как локально, так и на CI-сервере). Здесь достаточно логов в консоли и логов GitHub Actions? Или нужно что-то специальное добавлять?
