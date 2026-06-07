# Contexto del proyecto Android

## Objetivo de la app

La aplicación sirve para gestionar la hora de entrada y salida de los empleados asi como para tener un control del calendario y diversas capacidades mas.
Esta descripcion se actualizara segun se vaya prograsando mas en la aplicación.

## Arquitectura

Indicar lo que aplique:

- MVVM
- Clean Architecture
- Repository Pattern
- Use Cases
- Room
- Retrofit
- Hilt/Dagger
- Coroutines
- Flow/LiveData
- Jetpack Compose o XML Views

## Módulos

- app: módulo principal
- core: lógica común, si existe
- data: acceso a red/base de datos, si existe
- domain: casos de uso, si existe
- feature-x: pantallas funcionales, si existen

## Reglas importantes

- No modificar APIs públicas sin avisar.
- Mantener compatibilidad con minSdk configurado.
- Evitar cambios masivos.
- Preferir soluciones simples.
- Toda corrección debe compilar.
- No añadir dependencias salvo que sea imprescindible.

## Comandos de validación

```bash
./gradlew clean
./gradlew lintDebug
./gradlew testDebugUnitTest
./gradlew assembleDebug