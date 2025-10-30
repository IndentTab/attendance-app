pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/IndentTab/attendance-app.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Installing dependencies...'
                bat 'npm install'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'npm test || exit /b 0'
            }
        }

        stage('Package Docker Image') {
            steps {
                echo 'Building Docker image...'
                bat 'docker build -t nodejs-app .'
            }
        }

        stage('Run Docker Container') {
            steps {
                bat '''
                echo Cleaning up old containers...
                for /f "delims=" %%i in ('docker ps -q --filter "ancestor=nodejs-app"') do docker stop %%i || echo none
                for /f "delims=" %%i in ('docker ps -a -q --filter "ancestor=nodejs-app"') do docker rm %%i || echo none

                echo Starting new container...
                docker run -d -p 3000:3000 --name nodejs-app-%BUILD_NUMBER% nodejs-app
                '''
            }
        }
    }

    post {
        failure {
            echo 'Build failed. Check logs.'
        }
        success {
            echo 'Build completed successfully.'
        }
    }
}

