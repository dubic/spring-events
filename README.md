# spring-events
A POC for spring events sub system with the following events:

### Spring application events
1. ApplicationContextInitializedEvent
2. ApplicationEnvironmentPreparedEvent
3. ApplicationFailedEvent
4. ApplicationPreparedEvent
5. ApplicationReadyEvent
6. ApplicationStartedEvent
7. ApplicationStartingEvent
8. ApplicationContextEvent

### Testing events
1. TestContextEvent
2. AfterTestClassEvent
3. AfterTestExecutionEvent
4. AfterTestMethodEvent
5. BeforeTestClassEvent
6. PrepareTestInstanceEvent

### Security events 
1. AuthenticationFailureBadCredentialsEvent
2. AuthorizationDeniedEvent<T>

### Relational data events
1. RepositoriesPopulatedEvent
2. BeforeSaveEvent<E>

### Asynchronous events

### Events within a transaction (even async)

### Sticky events that survive application shutdown.
