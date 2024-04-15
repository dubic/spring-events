# spring-events
A POC for spring events sub system with the following events:

1. Spring application events: **ApplicationContextInitializedEvent**, **ApplicationEnvironmentPreparedEvent**, **ApplicationFailedEvent**, **ApplicationPreparedEvent**, **ApplicationReadyEvent**, **ApplicationStartedEvent**, **ApplicationStartingEvent**, **ApplicationContextEvent**
2. Testing events: **TestContextEvent**, **AfterTestClassEvent**, **AfterTestExecutionEvent**, **AfterTestMethodEvent**, **BeforeTestClassEvent**, **PrepareTestInstanceEvent**
3. Security events: **AuthenticationFailureBadCredentialsEvent**, **AuthorizationDeniedEvent<T>**
4. Relational data events: **RepositoriesPopulatedEvent**, **BeforeSaveEvent<E>**

### Asynchronous events

### Events within a transaction (even async)

### Sticky events that survive application shutdown.
