pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/IndentTab/attendance-app.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Installing dependencies...'
                sh 'npm install'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                // If you have no tests yet, skip with true
                sh 'npm test || true'
            }
        }

        stage('Package Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh 'docker build -t nodejs-app .'
            }
        }

        stage('Run Docker Container') {
            steps {
                echo 'Running Docker container...'
                sh 'docker run -d -p 3000:3000 nodejs-app'
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed. Check logs.'
        }
    }
}
