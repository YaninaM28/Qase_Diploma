/**
 * Qase Automation Framework - CI/CD Pipeline
 * 
 * Jenkins Pipeline for automated test execution with:
 * - Multiple browser support (Chrome, Firefox)
 * - Allure reporting
 * - Email notifications
 * - Artifact archiving
 * 
 * Requirements:
 * - Maven 3.9.6+
 * - Java 17+
 * - Chrome or Firefox browser
 * - Jenkins plugins: Email Extension, Allure
 * - Jenkins credentials: QASE_USER, QASE_PASSWORD, QASE_TOKEN
 */

pipeline {
    agent any

    options {
        // Limit build history to save disk space
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '5'))
        // Timeout the build after 30 minutes
        timeout(time: 30, unit: 'MINUTES')
        // Disable concurrent builds for this job
        disableConcurrentBuilds()
        // Add timestamps to console output
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

    environment {
        // Build information
        BUILD_INFO = "Build #${env.BUILD_NUMBER} on ${env.NODE_NAME}"
        // Allure results directory
        ALLURE_RESULTS_DIR = "${WORKSPACE}/target/allure-results"
        // Log file path
        LOG_FILE = "${WORKSPACE}/target/tests.log"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "=== STAGE: Checkout Code ==="
                    echo "Build: ${BUILD_INFO}"
                }
                // Clone repository
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: 'https://github.com/YaninaM28/Qase_Diploma.git']]
                ])
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "=== STAGE: Build Project ==="
                }
                // Clean and compile
                sh 'mvn clean compile -DskipTests=true'
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

        stage('Generate Allure Report') {
            when {
                // Always generate report, even if tests fail
                always {}
            }
            steps {
                script {
                    echo "=== STAGE: Generate Allure Report ==="
                }
                sh 'mvn allure:report || true'
            }
        }
    }

    post {
        always {
            script {
                echo "=== POST: Always Execute ==="
            }

            // Archive test results
            junit '**/target/surefire-reports/TEST-*.xml'

            // Archive Allure results
            archiveArtifacts artifacts: 'target/allure-results/**', 
                             allowEmptyArchive: true,
                             fingerprint: true

            // Archive logs
            archiveArtifacts artifacts: 'target/tests.log',
                             allowEmptyArchive: true

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
            script {
                echo "=== POST: Tests Passed ✅ ==="
            }
            
            // Send success email
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
                    <p><b>Build URL:</b> <a href="${BUILD_URL}">${BUILD_URL}</a></p>
                    <p><b>Browser:</b> ${BROWSER}</p>
                    <p><b>Headless:</b> ${HEADLESS}</p>
                    <br/>
                    <h3>⚠️ Failed Tests Found!</h3>
                    <p>Please review the test results and logs to identify the failures.</p>
                    <br/>
                    <p><b>Allure Report:</b> <a href="${BUILD_URL}allure/">View Report</a></p>
                    <p><b>Test Results:</b> <a href="${BUILD_URL}testReport/">Test Report</a></p>
                    <p><b>Console Output:</b> <a href="${BUILD_URL}console">View Logs</a></p>
                    <br/>
                    <p><b>Error Log Excerpt:</b></p>
                    <pre>${BUILD_LOG, maxLines=50}</pre>
                ''',
                recipientProviders: [developers(), requestor(), brokenBuildSuspects()],
                attachmentsPattern: '**/target/site/allure-report/**',
                mimeType: 'text/html'
            )

            // Mark build as unstable if tests fail
            currentBuild.result = 'UNSTABLE'
        }

        unstable {
            script {
                echo "=== POST: Build Unstable ⚠️ ==="
            }
        }

        cleanup {
            script {
                echo "=== POST: Cleanup ==="
                echo "Workspace: ${WORKSPACE}"
            }
        }
    }
}

