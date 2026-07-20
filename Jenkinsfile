pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven 3.9.6"
    }

    parameters{
        choice(choices: ['chrome', 'firefox'], name: 'BROWSER')
    }

    stages {
        stage('Run test') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/YaninaM28/Qase_Diploma.git'

                withCredentials([
                    string(credentialsId: 'USER', variable: 'QASE_USER'),
                    string(credentialsId: 'PASSWORD', variable: 'QASE_PASSWORD'),
                    string(credentialsId: 'TOKEN', variable: 'QASE_TOKEN')
                    ]) {

                // Run Maven on a Unix agent.
                bat """
                mvn clean test ^
                -Dbrowser=${params.BROWSER} ^
                -Duser=%QASE_USER% ^
                -Dpassword=%QASE_PASSWORD% ^
                -Dtoken=%QASE_TOKEN%
                """


                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
                }
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    allure includeProperties: false, jdk: '', resultPolicy: 'LEAVE_AS_IS', results: [[path: 'target/allure-results']]
                }
            }
        }
    }
}
