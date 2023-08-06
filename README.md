# ln-dl, light novel downloader
Light novel downloader

## Development
Development is done using IntelliJ IDEA.


Recommended IntelliJ IDEA plugins:
* CheckStyle-IDEA   
* google-java-format


Useful commands:
* `mvn clean compile` Rebuild
* `mvn spring-boot:run -Dspring-boot.run.profiles=dev` Run application in dev mode
* `mvn test -Punit-test` Run unit tests
* `mvn test -Pintegration-test` Run integration tests
* `mvn clean checkstyle:check -Pcode-linter` Run linter
* `mvn clean verify -Pcode-analysis -Dmaven.test.skip` Run code analysis



### Notes: 
 * Use selenium instead of htmlunit to make it easier to solve challenges & store headers/cookies