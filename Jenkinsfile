pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.5'
        jdk 'JDK-11'
    }
    
    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge', 'headless-chrome'], description: 'Browser for Web tests')
        choice(name: 'PLATFORM', choices: ['web', 'android', 'ios', 'api', 'all'], description: 'Platform to test')
        string(name: 'TEST_SUITE', defaultValue: 'testng.xml', description: 'TestNG XML file to execute')
    }
    
    environment {
        BROWSER = "${params.BROWSER}"
        PLATFORM = "${params.PLATFORM}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from repository...'
                checkout scm
            }
        }
        
        stage('Setup') {
            steps {
                echo 'Setting up environment...'
                sh 'java -version'
                sh 'mvn -version'
            }
        }
        
        stage('Clean') {
            steps {
                echo 'Cleaning previous build artifacts...'
                sh 'mvn clean'
            }
        }
        
        stage('Compile') {
            steps {
                echo 'Compiling the project...'
                sh 'mvn compile'
            }
        }
        
        stage('Run Tests') {
            steps {
                echo "Running ${params.PLATFORM} tests with ${params.BROWSER} browser..."
                script {
                    try {
                        sh """
                            mvn test -Dsurefire.suiteXmlFiles=${params.TEST_SUITE} \
                                     -Dbrowser=${params.BROWSER} \
                                     -Dplatform=${params.PLATFORM}
                        """
                    } catch (Exception e) {
                        currentBuild.result = 'UNSTABLE'
                        echo "Tests failed but continuing pipeline: ${e.message}"
                    }
                }
            }
        }
        
        stage('Generate Reports') {
            steps {
                echo 'Generating test reports...'
                // Extent Reports are already generated during test execution
                echo 'Extent Reports generated in test-output directory'
            }
        }
    }
    
    post {
        always {
            echo 'Publishing test results...'
            
            // Publish TestNG results
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output',
                reportFiles: 'ExtentReport_*.html',
                reportName: 'Extent Report',
                reportTitles: 'Test Automation Report'
            ])
            
            // Archive artifacts
            archiveArtifacts artifacts: 'test-output/**/*', allowEmptyArchive: true
            
            // Clean workspace
            cleanWs()
        }
        
        success {
            echo 'Build succeeded!'
            emailext(
                subject: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: """
                    <p>Good news! The test execution completed successfully.</p>
                    <p><b>Job:</b> ${env.JOB_NAME}</p>
                    <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>
                    <p><b>Platform:</b> ${params.PLATFORM}</p>
                    <p><b>Browser:</b> ${params.BROWSER}</p>
                    <p>Check console output at <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></p>
                """,
                to: 'team@example.com',
                mimeType: 'text/html'
            )
        }
        
        failure {
            echo 'Build failed!'
            emailext(
                subject: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: """
                    <p>Unfortunately, the test execution failed.</p>
                    <p><b>Job:</b> ${env.JOB_NAME}</p>
                    <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>
                    <p><b>Platform:</b> ${params.PLATFORM}</p>
                    <p><b>Browser:</b> ${params.BROWSER}</p>
                    <p>Check console output at <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></p>
                """,
                to: 'team@example.com',
                mimeType: 'text/html'
            )
        }
        
        unstable {
            echo 'Build is unstable (some tests failed)'
        }
    }
}
