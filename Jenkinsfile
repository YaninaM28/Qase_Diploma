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
                echo "✅ Repository checked out"
            }
        }

        stage('Build') {
            steps {
                echo "Building project..."
                bat 'mvn clean compile -DskipTests'
                echo "✅ Build successful"
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running tests on ${params.BROWSER} (Headless: ${params.HEADLESS})"
                withCredentials([
                    string(credentialsId: 'USER', variable: 'QASE_USER'),
                    string(credentialsId: 'PASSWORD', variable: 'QASE_PASSWORD'),
                    string(credentialsId: 'TOKEN', variable: 'QASE_TOKEN')
                ]) {
                    bat '''
                        mvn clean test ^
                        -Dbrowser=%BROWSER% ^
                        -Duser=%QASE_USER% ^
                        -Dpassword=%QASE_PASSWORD% ^
                        -Dtoken=%QASE_TOKEN% ^
                        -Dselenide.headless=%HEADLESS% ^
                        -Dmaven.test.failure.ignore=true
                    '''
                }
                echo "✅ Tests completed"
            }
        }
    }

    post {
        always {
            echo "Processing test results..."
            
            script {
                try {
                    junit testResults: '**/target/surefire-reports/TEST-*.xml', 
                          allowEmptyResults: true
                    echo "✅ JUnit results processed"
                } catch (Exception e) {
                    echo "⚠️  Could not process JUnit results: ${e.message}"
                }
            }

            script {
                try {
                    allure(
                        includeProperties: true,
                        jdk: '',
                        results: [[path: 'target/allure-results']]
                    )
                    echo "✅ Allure report generated"
                } catch (Exception e) {
                    echo "⚠️  Could not generate Allure report: ${e.message}"
                }
            }

            archiveArtifacts artifacts: 'target/surefire-reports/**/*.xml, target/allure-results/**', 
                             allowEmptyArchive: true

            cleanWs(
                deleteDirs: true,
                patterns: [[pattern: 'target/allure-results/**', type: 'EXCLUDE'],
                           pattern: '**/.*', type: 'INCLUDE']]
            )
        }

        unstable {
            echo "⚠️  Build unstable - Some tests failed"
            emailext(
                subject: "⚠️  Test Results - Build #${BUILD_NUMBER}",
                body: '''
                    <h2>⚠️  Some Tests Failed</h2>
                    <p><b>Job:</b> ${JOB_NAME}</p>
                    <p><b>Build:</b> <a href="${BUILD_URL}">#${BUILD_NUMBER}</a></p>
                    <p><b>Browser:</b> ${BROWSER}</p>
                    <p><b>Allure Report:</b> <a href="${BUILD_URL}allure/">View Report</a></p>
                ''',
                recipientProviders: [developers(), requestor()],
                mimeType: 'text/html'
            )
        }

        success {
            emailext(
                subject: "✅ Build #${BUILD_NUMBER} - All Tests Passed",
                body: '''
                    <h2>✅ All Tests PASSED</h2>
                    <p><b>Job:</b> ${JOB_NAME}</p>
                    <p><b>Build:</b> <a href="${BUILD_URL}">#${BUILD_NUMBER}</a></p>
                    <p><b>Browser:</b> ${BROWSER}</p>
                    <p><b>Allure Report:</b> <a href="${BUILD_URL}allure/">View Report</a></p>
                ''',
                recipientProviders: [developers(), requestor()],
                mimeType: 'text/html'
            )
        }

        failure {
            emailext(
                subject: "❌ Build #${BUILD_NUMBER} - Pipeline Failed",
                body: '''
                    <h2>❌ Build FAILED</h2>
                    <p><b>Job:</b> ${JOB_NAME}</p>
                    <p><b>Build:</b> <a href="${BUILD_URL}">#${BUILD_NUMBER}</a></p>
                    <p><b>Browser:</b> ${BROWSER}</p>
                    <p><b>Console:</b> <a href="${BUILD_URL}console">View Logs</a></p>
                    <p><b>Allure Report:</b> <a href="${BUILD_URL}allure/">View Report</a></p>
                ''',
                recipientProviders: [developers(), requestor(), brokenBuildSuspects()],
                mimeType: 'text/html'
            )
        }
    }
}

