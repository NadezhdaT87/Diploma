# Инструкция по запуску автотестов для приложения "Мобильный хоспис"

## 1. Предварительные условия:

#### 1.1 Склонировать репозиторий

[git clone https://github.com/iteco/fmh-android.git
cd fmh-android
](https://github.com/NadezhdaT87/Diploma.git)

#### 1.2 Открыть проект в Android Studio

#### 1.3 Создать эмулятор

#### 1.4 Запустить эмулятор

#### 1.5 Установить приложения
В Android Studio:
- Выбрать конфигурацию app
- Выбрать ваш эмулятор
- Нажать Run 'app' (Shift+F10)
  
### Данные для авторизации:
- В поле "Логин" ввести: login2
- В поле "Пароль" ввести: password2

## 2. Запуск тестов:
Способ 1: Через Android Studio
Открыть папку:

app/src/androidTest/java/ru/iteco/fmhandroid/ui/tests
ПКМ → Run 'Tests in 'ru.iteco.fmhandroid...'

Способ 2: Через терминал

./gradlew connectedAndroidTest

## 3. Генерация отчета
#### 3.1 Получить результаты
выгрузить файлы отчета с устройства в корень проекта:
/data/data/ru.iteco.fmhandroid/files/allure-results ./allure-results
#### 3.2 Смотреть отчет
allure serve allure-results
