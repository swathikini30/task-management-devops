
pipeline {
    agent any

    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                sh './mvnw clean test'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw package -DskipTests'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    pkill -f 'task-api-0.0.1-SNAPSHOT.jar' || true
                    nohup java -jar target/task-api-0.0.1-SNAPSHOT.jar \
                    --server.port=8080 > app.log 2>&1 &
                '''
                sleep 10
                sh 'curl -f http://localhost:8080/health'
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar',
                             fingerprint: true
        }

        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}

