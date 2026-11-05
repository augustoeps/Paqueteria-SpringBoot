/*
 * ============================================================================
 *  Jenkinsfile - Pipeline de CI/CD del proyecto Paquetería
 * ============================================================================
 *  Este archivo es la "receta" que Jenkins ejecuta en cada rama y en cada
 *  pull request del repositorio. Es el equivalente al .github/workflows/ci.yml
 *  de GitHub Actions, pero con la sintaxis de Jenkins (Groovy declarativo).
 *
 *  Flujo:
 *    1. Jenkins descarga el código de la rama o de la pull request (automático).
 *    2. Compila el proyecto y ejecuta todos los tests con Maven.
 *    3. Publica el informe de tests en la web de Jenkins.
 *    4. Si todo ha ido bien, guarda el .jar generado.
 *    5. SOLO en master: publica el .jar en Artifactory (CD).
 *    6. El plugin de Bitbucket informa del resultado (✅ / ❌) en la pull request.
 * ============================================================================
 */

pipeline {

    /*
     * AGENT: en qué máquina se ejecuta el pipeline.
     * "any" = en cualquier máquina disponible. En nuestro caso es el propio
     * contenedor de Jenkins. En una empresa suele haber varios "agentes"
     * (máquinas de trabajo) y se elige uno según lo que haga falta.
     * Equivale a "runs-on: ubuntu-latest" en GitHub Actions.
     */
    agent any

    /*
     * TOOLS: herramientas que necesita el pipeline.
     * 'maven-3' es el nombre que le dimos a Maven en
     * Manage Jenkins → Tools. Tiene que coincidir EXACTAMENTE.
     * Jenkins lo descarga la primera vez y lo reutiliza después.
     * Java 21 no hace falta declararlo: ya viene en la imagen de Jenkins.
     */
    tools {
        maven 'maven-3'
    }

    /*
     * OPTIONS: opciones generales del pipeline.
     * buildDiscarder: guarda solo las 10 últimas ejecuciones de cada rama,
     * para que el disco no se llene con logs y artefactos antiguos.
     */
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    /*
     * STAGES: las etapas del pipeline, que se ejecutan en orden.
     * Cada etapa aparece como una columna en la vista del pipeline en Jenkins.
     * Si una etapa falla, las siguientes no se ejecutan y el resultado es ❌.
     *
     * Nota: no hace falta una etapa para descargar el código. En un
     * Multibranch Pipeline, Jenkins lo hace automáticamente antes de empezar
     * (aparece como "Declarative: Checkout SCM").
     */
    stages {

        stage('Compilar y ejecutar tests') {
            steps {
                /*
                 * sh: ejecuta un comando en la terminal Linux del agente.
                 * mvn -B verify:
                 *   - mvn    → Maven, configurado en "tools".
                 *   - -B     → modo batch: sin preguntas por pantalla y con
                 *              un log más limpio, ideal para pipelines.
                 *   - verify → recorre las fases de Maven hasta "verify":
                 *              compila, ejecuta los tests de JUnit y genera
                 *              el .jar. Si un solo test falla, Maven termina
                 *              con error y la etapa queda en rojo.
                 * Es el mismo comando que en GitHub Actions.
                 */
                sh 'mvn -B verify'
            }
        }

        /*
         * PUBLICAR EN ARTIFACTORY (CD): solo en la rama master.
         * En las ramas y pull requests esta etapa se omite: nunca se publica
         * código que todavía no ha sido revisado y aceptado.
         * Solo se llega aquí si la etapa anterior (tests) ha ido bien.
         */
        stage('Publicar en Artifactory') {

            /*
             * when: condición para ejecutar la etapa.
             * branch 'master' → solo cuando el pipeline se ejecuta sobre master,
             * es decir, después de un merge. Equivale al
             * "if: github.event_name == 'push'" de GitHub Actions.
             */
            when {
                branch 'master'
            }

            steps {
                /*
                 * withCredentials: saca la credencial 'artifactory-jenkins' del
                 * almacén de credenciales de Jenkins y la pone en dos variables
                 * de entorno, SOLO mientras dura este bloque:
                 *   - ARTIFACTORY_USER     → el usuario 'jenkins' de Artifactory
                 *   - ARTIFACTORY_PASSWORD → su contraseña
                 * En el log, la contraseña aparecerá oculta como ****.
                 * Equivale a los "secrets" de GitHub Actions.
                 */
                withCredentials([usernamePassword(
                        credentialsId: 'artifactory-jenkins',
                        usernameVariable: 'ARTIFACTORY_USER',
                        passwordVariable: 'ARTIFACTORY_PASSWORD')]) {

                    /*
                     * mvn deploy: empaqueta y SUBE el .jar y el pom.xml al
                     * repositorio de artefactos.
                     *   - -DskipTests        → no repite los tests: ya pasaron
                     *                          en la etapa anterior.
                     *   - -P artifactory     → activa el perfil "artifactory" del
                     *                          pom.xml, con las URLs de Artifactory
                     *                          en lugar de las de GitHub Packages.
                     *   - -s ci-settings.xml → usa ese archivo de configuración de
                     *                          Maven, que lee el usuario y la
                     *                          contraseña de las variables de entorno.
                     * Maven elige el repositorio según la versión del pom.xml:
                     *   - termina en -SNAPSHOT → libs-snapshot-local
                     *   - versión definitiva   → libs-release-local
                     */
                    sh 'mvn -B deploy -DskipTests -P artifactory -s ci-settings.xml'

                } // fin de withCredentials
            } // fin de steps
        } // fin de stage('Publicar en Artifactory')

    } // fin de stages

    /*
     * POST: acciones que se ejecutan AL TERMINAR el pipeline,
     * según cómo haya ido.
     */
    post {

        /*
         * always: se ejecuta SIEMPRE, haya ido bien o mal.
         * junit: lee los informes XML que genera Maven (plugin Surefire) en
         * target/surefire-reports/ y los muestra en Jenkins: cuántos tests
         * pasaron, cuáles fallaron, con gráficos de evolución.
         * allowEmptyResults: true → si la compilación falla antes de llegar a
         * los tests, no hay informes. Así no se añade un segundo error confuso.
         */
        always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
        }

        /*
         * success: solo si TODO el pipeline ha ido bien.
         * archiveArtifacts: guarda el .jar generado en Jenkins, para poder
         * descargarlo desde la ejecución. Equivale a "actions/upload-artifact".
         * fingerprint: true → Jenkins calcula una "huella" (hash) del archivo,
         * para saber en qué ejecución se generó cada .jar (trazabilidad).
         */
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }

    } // fin de post

} // fin de pipeline