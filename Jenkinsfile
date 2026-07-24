pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '5'))
        timeout(time: 30, unit: 'MINUTES')
        disableConcurrentBuilds()
        timestamps()
    }

    tools {
        // Use Maven 3.9.6 (must be configured in Jenkins)
        maven "maven 3.9.6"
        jdk "Java 17"
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
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: 'https://github.com/YaninaM28/Qase_Diploma.git']]
                ])
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
                    string(credentialsId: 'QASE_USER', variable: 'QASE_USER_VAL'),
                    string(credentialsId: 'QASE_PASSWORD', variable: 'QASE_PASSWORD_VAL'),
                    string(credentialsId: 'QASE_TOKEN', variable: 'QASE_TOKEN_VAL')
                ]) {
                    sh '''
                        echo "Starting test execution..."
                        mvn clean test \
                            -Dbrowser=${BROWSER} \
                            -Duser=${QASE_USER_VAL} \
                            -Dpassword=${QASE_PASSWORD_VAL} \
                            -Dtoken=${QASE_TOKEN_VAL} \
                            -Dselenide.headless=${HEADLESS} \
                            || true
                    '''
                }
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/TEST-*.xml'

            // Generate Allure report in Jenkins
            allure([
                resultPattern: 'target/allure-results',
                reportBuildPolicy: 'ALWAYS',
                reportPath: 'target/site/allure-report'
            ])

            // Clean workspace to save disk space
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

            // Send failure email with details
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

