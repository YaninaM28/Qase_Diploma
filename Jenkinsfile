pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '5'))
        timeout(time: 30, unit: 'MINUTES')
        disableConcurrentBuilds()
        timestamps()
    }

    tools {
        maven "maven 3.9.6"
        jdk "JDK"
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Choose browser for test execution'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run tests in headless mode (faster, no UI)'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/YaninaM28/Qase_Diploma.git']]
                ])
                echo "✅ Repository checked out successfully"
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "=== STAGE: Build Project ==="
                }
                bat 'mvn clean compile -DskipTests'
                echo "✅ Project compiled successfully"
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    echo "=== STAGE: Run Automation Tests ==="
                    echo "Browser: ${params.BROWSER}"
                    echo "Headless Mode: ${params.HEADLESS}"
                }

                withCredentials([
                    string(credentialsId: 'USER', variable: 'QASE_USER'),
                    string(credentialsId: 'PASSWORD', variable: 'QASE_PASSWORD'),
                    string(credentialsId: 'TOKEN', variable: 'QASE_TOKEN')
                ]) {
                    bat '''
                        echo "Starting test execution..."
                        mvn clean test ^
                        -Dbrowser=%BROWSER% ^
                        -Duser=%QASE_USER% ^
                        -Dpassword=%QASE_PASSWORD% ^
                        -Dtoken=%QASE_TOKEN% ^
                        -Dselenide.headless=%HEADLESS%
                    '''
                }
                echo "✅ Tests completed"
            }
        }

        stage('Generate Allure Report') {
            steps {
                script {
                    echo "=== STAGE: Generate Allure Report ==="
                }
                allure(
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                )
                echo "✅ Allure report generated"
            }
        }

        stage('Archive Artifacts') {
            steps {
                script {
                    echo "=== STAGE: Archive Test Results ==="
                }
                archiveArtifacts artifacts: 'target/surefire-reports/**/*.xml, target/allure-results/**', 
                                 allowEmptyArchive: true
                echo "✅ Artifacts archived"
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/TEST-*.xml'

           allure(
               includeProperties: false,
               jdk: '',
               results: [[path: 'target/allure-results']]
           )

            cleanWs(
                deleteDirs: true,
                patterns: [[pattern: '**/.*', type: 'INCLUDE']]
            )
        }

        success {
            emailext(
                subject: "✅ Test Execution Successful - Build #${BUILD_NUMBER}",
                body: '''
                    <h2>✅ Test Execution PASSED</h2>
                    <p><b>Job:</b> ${JOB_NAME}</p>
                    <p><b>Build Number:</b> ${BUILD_NUMBER}</p>
                    <p><b>Build URL:</b> <a href="${BUILD_URL}">${BUILD_URL}</a></p>
                    <p><b>Browser:</b> ${BROWSER}</p>
                    <p><b>Headless:</b> ${HEADLESS}</p>
                    <p><b>Duration:</b> ${BUILD_DURATION}</p>
                    <br/>
                    <p><b>Allure Report:</b> <a href="${BUILD_URL}allure/">View Report</a></p>
                    <p><b>Console Output:</b> <a href="${BUILD_URL}console">View Logs</a></p>
                ''',
                recipientProviders: [developers(), requestor()],
                mimeType: 'text/html'
            )
        }

        failure {
            script {
                echo "=== POST: Tests Failed ❌ ==="
            }

            emailext(
                subject: "❌ Test Execution Failed - Build #${BUILD_NUMBER}",
                body: '''
                    <h2>❌ Test Execution FAILED</h2>
                    <p><b>Job:</b> ${JOB_NAME}</p>
                    <p><b>Build Number:</b> ${BUILD_NUMBER}</p>
                    <p><b>Browser:</b> ${BROWSER}</p>
                    <p><b>Headless:</b> ${HEADLESS}</p>
                    <br/>
                    <p><b>Allure Report:</b> <a href="${BUILD_URL}allure/">View Report</a></p>
                    <p><b>Console Output:</b> <a href="${BUILD_URL}console">View Logs</a></p>
                    <br/>
                    <p><b>Error Log Excerpt:</b></p>
                    <pre>${BUILD_LOG, maxLines=50}</pre>
                ''',
                recipientProviders: [developers(), requestor(), brokenBuildSuspects()],
                attachmentsPattern: '**/target/site/allure-report/**',
                mimeType: 'text/html'
            )
        }
    }
}

