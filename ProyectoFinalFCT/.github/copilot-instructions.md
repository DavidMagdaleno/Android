# Instrucciones para revisión del proyecto Android

Responde siempre en español.

Prioridad principal:
1. Detectar bugs reales.
2. Detectar riesgos de seguridad.
3. Detectar fugas de memoria, problemas de ciclo de vida Android y mal uso de coroutines/threads.
4. Detectar problemas de arquitectura o mantenibilidad.
5. Proponer cambios mínimos y seguros.

No reescribas archivos completos salvo que se solicite.
No propongas refactors grandes si no son necesarios.
No inventes dependencias.
No asumas contexto que no esté en los archivos proporcionados.

Cuando revises código:
- Señala el archivo y la línea aproximada.
- Explica el problema.
- Indica severidad: crítica, alta, media o baja.
- Propón una corrección concreta.
- Si no estás seguro, dilo claramente.

Stack esperado:
- Android Studio
- Kotlin o Java
- Gradle
- Android Jetpack si aplica
- MVVM si aplica
- Coroutines/Flow si aplica
- Room/Retrofit/Hilt si aplica

Reglas Android:
- No usar Context de Activity en singletons.
- Evitar memory leaks en Activities, Fragments, Adapters y ViewModels.
- Respetar ciclo de vida: viewLifecycleOwner en Fragment.
- No bloquear el hilo principal.
- No hacer llamadas de red o base de datos en Main thread.
- Validar permisos y entradas de usuario.
- Evitar logs con datos sensibles.
- No hardcodear secretos, tokens, URLs privadas o credenciales.
- Usar strings.xml para textos visibles.
- Revisar compatibilidad minSdk/targetSdk.