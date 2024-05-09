# Daniel Felipe Valencia Garcia

# Aplicación prueba técnica Android, Wireless And Mobile

## Capas

[![N|Solid](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGYDqu23pK59XuSOu7jTuKLpPO0NfTTGOfT5EUz4jv2Q&s)](https://developer.android.com/studio?hl=es-419)

Esta aplicación esta contrudia con Android nativo utilizando Kotlin y Jetpack Compose.

# Instalaciones necesarias

- MS Windows
- JDK
- Android Studio

## Características

Esta aplicación consume la API de restcountries.com la cual presenta un listado de paises y la información relacionada. En esta oportunidad vamos a utilizar el siguiente endpoint que nos retorna el listado completo de paises https://restcountries.com/v3.1/all .

## Detalles técnicos

Dividi mi aplicación en tres capas principales

- Capa Network - Donde utilice Retrofit y Gson, para realizar las consultas al endpoint.
- Capa ViewModel - En esta capa la aplicacion controla la lógica de los componentes, los estados, persistencia de datos y los llamados a la capa Network.
- Capa UI - En esta capa presentamos la información traida del endpoint.
- Capa de navegación - En esta capa se controlan las pantallas que se muestran.
- Capa Model - En esta capa defini los tipos de datos que utilizo.

## Instalación

Pasos para la instalación:

- Abrir el proyecto en Android Studio
- Sincronizar dependencias
- Correr la aplicación con un dispositivo o un emulador conectado

## Plugins

Integre los siguientes plugins

| Plugin                                         | Versión |
| ---------------------------------------------- | ------- |
| com.squareup.retrofit2:retrofit                | 2.9.0   |
| com.google.code.gson:gson                      | 2.9.0   |
| androidx.navigation:navigation-compose         | 2.5.0   |
| androidx.lifecycle:lifecycle-viewmodel-compose | 2.6.1   |
